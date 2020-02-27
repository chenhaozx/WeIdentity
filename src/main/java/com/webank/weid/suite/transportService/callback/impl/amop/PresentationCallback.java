/*
 *       Copyright© (2018-2020) WeBank Co., Ltd.
 *
 *       This file is part of weid-java-sdk.
 *
 *       weid-java-sdk is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       weid-java-sdk is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with weid-java-sdk.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.webank.weid.suite.transportService.callback.impl.amop;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webank.weid.constant.DataDriverConstant;
import com.webank.weid.constant.ErrorCode;
import com.webank.weid.constant.ParamKeyConstant;
import com.webank.weid.protocol.amop.GetPolicyAndChallengeArgs;
import com.webank.weid.protocol.base.PolicyAndChallenge;
import com.webank.weid.protocol.base.PresentationPolicyE;
import com.webank.weid.protocol.response.GetPolicyAndChallengeResponse;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.callback.AmopCallback;
import com.webank.weid.suite.api.persistence.Persistence;
import com.webank.weid.suite.persistence.sql.driver.MysqlDriver;
import com.webank.weid.util.DataToolUtils;

/**
 * 用于处理机构根据policyId获取policy的回调.
 *
 * @author v_wbgyang
 */
public class PresentationCallback extends AmopCallback {

    private static final Logger logger =
        LoggerFactory.getLogger(PresentationCallback.class);
    private static Persistence persistence;
    private CallbackService callbackService;

    private static Persistence getDataDriver() {
        if (persistence == null) {

            persistence = new MysqlDriver();
        }
        return persistence;
    }

    @Override
    public GetPolicyAndChallengeResponse onPush(GetPolicyAndChallengeArgs arg) {

        logger.info("PresentationCallback param:{}", arg);
        GetPolicyAndChallengeResponse response = new GetPolicyAndChallengeResponse();
        if (callbackService == null) {
            logger.error("PresentationCallback policyService is null");
            response.setErrorCode(ErrorCode.POLICY_SERVICE_NOT_EXISTS.getCode());
            response.setErrorMessage(ErrorCode.POLICY_SERVICE_NOT_EXISTS.getCodeDesc());
            return response;
        }

        PolicyAndChallenge policyAndChallenge;
        try {
            policyAndChallenge =
                callbackService
                    .policyAndChallengeOnPush(arg.getPolicyId(), arg.getTargetUserWeId());
        } catch (Exception e) {
            logger.error("the policy service call fail, please check the error log.", e);
            response.setErrorCode(ErrorCode.POLICY_SERVICE_CALL_FAIL.getCode());
            response.setErrorMessage(ErrorCode.POLICY_SERVICE_CALL_FAIL.getCodeDesc());
            return response;
        }

        //生成一个seqId来关联这些数据，并落库存储
        String seqId = UUID.randomUUID().toString();
        //同时提前生成好credentialId
        String credentialId = UUID.randomUUID().toString();
        Map<String, Object> relatedDataMap = new HashMap<>();
        relatedDataMap.put(ParamKeyConstant.POLICY_ID, arg.getPolicyId());
        relatedDataMap.put(ParamKeyConstant.CHALLENGE, policyAndChallenge.getChallenge().toJson());
        relatedDataMap.put(ParamKeyConstant.TARGET_WEID, arg.getTargetUserWeId());
        //relatedDataMap.put("credentialId", credentialId);
        String relatedDataJson = DataToolUtils.serialize(relatedDataMap);
        ResponseData<Integer> resp = getDataDriver()
            .saveOrUpdate(DataDriverConstant.DOMAIN_CONSUMABLE_CREDENTIAL, seqId, relatedDataJson);
        if (resp.getErrorCode().intValue() != ErrorCode.SUCCESS.getCode()) {
            logger.error(
                "[PresentationCallback] save seqId and related data failed.");
            response.setErrorCode(ErrorCode.POLICY_SERVICE_CALL_FAIL.getCode());
            response.setErrorMessage(ErrorCode.POLICY_SERVICE_CALL_FAIL.getCodeDesc());
            return response;
        }
        PresentationPolicyE policy = policyAndChallenge.getPresentationPolicyE();
        policy.getExtra().put(ParamKeyConstant.SEQ_ID, seqId);
        policy.getExtra().put(ParamKeyConstant.KEY_CREDENTIAL_ID, credentialId);

        ResponseData<Integer> resp1 = persistence
            .saveOrUpdate(DataDriverConstant.DOMAIN_CONSUMABLE_CREDENTIAL, arg.getPolicyId(),
                policy.toJson());
        if (resp1.getErrorCode().intValue() != ErrorCode.SUCCESS.getCode()) {
            logger.error(
                "[PresentationCallback] save policy failed.");
            response.setErrorCode(ErrorCode.POLICY_SERVICE_CALL_FAIL.getCode());
            response.setErrorMessage(ErrorCode.POLICY_SERVICE_CALL_FAIL.getCodeDesc());
            return response;
        }

        response.setErrorCode(ErrorCode.SUCCESS.getCode());
        response.setErrorMessage(ErrorCode.SUCCESS.getCodeDesc());
        response.setPolicyAndChallenge(policyAndChallenge);
        return response;
    }

    public void registPolicyService(CallbackService service) {
        callbackService = service;
    }
}
