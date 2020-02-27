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

package com.webank.weid.suite.transportService.request.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webank.weid.constant.AmopMsgType;
import com.webank.weid.constant.ErrorCode;
import com.webank.weid.protocol.amop.GetPolicyAndChallengeArgs;
import com.webank.weid.protocol.amop.RequestConsumableCredentialArgs;
import com.webank.weid.protocol.amop.RequestVerifierOwnerResultArgs;
import com.webank.weid.protocol.amop.RequestZkpConsumableCredentialArgs;
import com.webank.weid.protocol.amop.TransferCreditCredentialArgs;
import com.webank.weid.protocol.base.PolicyAndChallenge;
import com.webank.weid.protocol.response.GetPolicyAndChallengeResponse;
import com.webank.weid.protocol.response.RequestConsumableCredentialResponse;
import com.webank.weid.protocol.response.RequestVerifierOwnerResultResponse;
import com.webank.weid.protocol.response.RequestZkpConsumableCredentialResponse;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.protocol.response.TransferCreditCredentialResponse;
import com.webank.weid.rpc.CredentialPojoService;
import com.webank.weid.rpc.callback.AmopCallback;
import com.webank.weid.service.BaseService;
import com.webank.weid.service.fisco.WeServer;
import com.webank.weid.service.impl.AmopServiceImpl;
import com.webank.weid.service.impl.CredentialPojoServiceImpl;
import com.webank.weid.suite.api.persistence.Persistence;
import com.webank.weid.suite.persistence.sql.driver.MysqlDriver;
import com.webank.weid.suite.transportService.request.intf.Transport;

/**
 * @author tonychen 2020年2月11日
 */
public class AmopTransportService extends BaseService implements Transport {

    private static final Logger logger = LoggerFactory.getLogger(AmopServiceImpl.class);


    /**
     * persistence service.
     */
    private static Persistence dataDriver;
    /**
     * credentialpojo service.
     */
    private static CredentialPojoService credentialPojoService = new CredentialPojoServiceImpl();

    private static Persistence getDataDriver() {
        if (dataDriver == null) {
            dataDriver = new MysqlDriver();
        }
        return dataDriver;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.request.intf.Transport#getPolicyAndChallenge(java.lang.String, java.lang.Integer, java.lang.String)
     */
    @Override
    public ResponseData<PolicyAndChallenge> getPolicyAndChallenge(String orgId, Integer policyId,
        String targetUserWeId) {

        try {
            if (StringUtils.isBlank(fiscoConfig.getCurrentOrgId())) {
                logger.error("the orgId is null, policyId = {}", policyId);
                return new ResponseData<PolicyAndChallenge>(null, ErrorCode.ILLEGAL_INPUT);
            }
            GetPolicyAndChallengeArgs args = new GetPolicyAndChallengeArgs();
            args.setFromOrgId(fiscoConfig.getCurrentOrgId());
            args.setToOrgId(orgId);
            args.setPolicyId(String.valueOf(policyId));
            args.setMessageId(super.getSeq());
            args.setTargetUserWeId(targetUserWeId);
            ResponseData<GetPolicyAndChallengeResponse> retResponse =
                this.getPolicyAndChallenge(orgId, args);
            if (retResponse.getErrorCode().intValue() != ErrorCode.SUCCESS.getCode()) {
                logger.error("AMOP response fail, policyId={}, errorCode={}, errorMessage={}",
                    policyId,
                    retResponse.getErrorCode(),
                    retResponse.getErrorMessage()
                );
                return new ResponseData<PolicyAndChallenge>(
                    null,
                    ErrorCode.getTypeByErrorCode(retResponse.getErrorCode().intValue())
                );
            }
            GetPolicyAndChallengeResponse result = retResponse.getResult();
            ErrorCode errorCode =
                ErrorCode.getTypeByErrorCode(result.getErrorCode().intValue());
            return new ResponseData<PolicyAndChallenge>(result.getPolicyAndChallenge(), errorCode);
        } catch (Exception e) {
            logger.error("getPresentationPolicy failed due to system error. ", e);
            return new ResponseData<PolicyAndChallenge>(null, ErrorCode.UNKNOW_ERROR);
        }
    }

    private ResponseData<GetPolicyAndChallengeResponse> getPolicyAndChallenge(
        String toOrgId,
        GetPolicyAndChallengeArgs args) {
        return this.getImpl(
            fiscoConfig.getCurrentOrgId(),
            toOrgId,
            args,
            GetPolicyAndChallengeArgs.class,
            GetPolicyAndChallengeResponse.class,
            AmopMsgType.GET_POLICY_AND_CHALLENGE,
            WeServer.AMOP_REQUEST_TIMEOUT
        );
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.request.intf.Transport#requestOriginalConsumableCredential(java.lang.String, com.webank.weid.protocol.amop.RequestConsumableCredentialArgs)
     */
    @Override
    public ResponseData<RequestConsumableCredentialResponse> requestOriginalConsumableCredential(
        String toOrgId,
        RequestConsumableCredentialArgs args) {

        if (StringUtils.isBlank(fiscoConfig.getCurrentOrgId())) {
            logger.error("the current orgId is null");
            return new ResponseData<RequestConsumableCredentialResponse>(null,
                ErrorCode.ILLEGAL_INPUT);
        }
        if (StringUtils.isBlank(toOrgId)) {
            logger.error("the toOrgId is null");
            return new ResponseData<RequestConsumableCredentialResponse>(null,
                ErrorCode.ILLEGAL_INPUT);
        }

        return this.getImpl(
            fiscoConfig.getCurrentOrgId(),
            toOrgId,
            args,
            RequestConsumableCredentialArgs.class,
            RequestConsumableCredentialResponse.class,
            AmopMsgType.GET_CONSUMABLE_CREDENTIAL,
            WeServer.AMOP_REQUEST_TIMEOUT
        );
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.request.intf.Transport#requestZkpConsumableCredential(java.lang.String, com.webank.weid.protocol.amop.RequestZkpConsumableCredentialArgs)
     */
    @Override
    public ResponseData<RequestZkpConsumableCredentialResponse> requestZkpConsumableCredential(
        String toOrgId,
        RequestZkpConsumableCredentialArgs args) {

        return null;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.request.intf.Transport#requestVerifierOwnerResult(java.lang.String, com.webank.weid.protocol.amop.RequestVerifierOwnerResultArgs)
     */
    @Override
    public ResponseData<RequestVerifierOwnerResultResponse> requestVerifierOwnerResult(
        String toOrgId,
        RequestVerifierOwnerResultArgs args) {

        return null;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.request.intf.Transport#transferCreditCredential(java.lang.String, com.webank.weid.protocol.amop.TransferCreditCredentialArgs)
     */
    @Override
    public ResponseData<TransferCreditCredentialResponse> transferCreditCredential(String toOrgId,
        TransferCreditCredentialArgs args) {

        return null;
    }

    public void registerCallback(Integer directRouteMsgType, AmopCallback directRouteCallback) {

        super.getPushCallback().registAmopCallback(directRouteMsgType, directRouteCallback);

    }

}
