/*
 *       Copyright© (2018-2019) WeBank Co., Ltd.
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

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.InvalidProtocolBufferException;
import com.webank.pkeygen.exception.PkeyGenException;
import com.webank.wedpr.common.WedprException;
import com.webank.wedpr.confidentialpayment.RedeemerClient;
import com.webank.wedpr.confidentialpayment.RedeemerResult;
import com.webank.wedpr.confidentialpayment.proto.CreditValue;
import com.webank.wedpr.example.confidentialpayment.ConfidentialPaymentExample;
import com.webank.wedpr.example.confidentialpayment.StorageExampleClient;
import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webank.weid.constant.DataDriverConstant;
import com.webank.weid.constant.ErrorCode;
import com.webank.weid.constant.ParamKeyConstant;
import com.webank.weid.protocol.amop.RequestConsumableCredentialArgs;
import com.webank.weid.protocol.base.Challenge;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.PresentationPolicyE;
import com.webank.weid.protocol.base.WeIdAuthentication;
import com.webank.weid.protocol.request.CreateConsumableCredentialPojoArgs;
import com.webank.weid.protocol.response.RequestConsumableCredentialResponse;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.CredentialPojoService;
import com.webank.weid.rpc.callback.AmopCallback;
import com.webank.weid.service.impl.CredentialPojoServiceImpl;
import com.webank.weid.service.impl.engine.BaseEngine;
import com.webank.weid.suite.api.persistence.Persistence;
import com.webank.weid.suite.persistence.sql.driver.MysqlDriver;
import com.webank.weid.util.DataToolUtils;

/**
 * @author tonychen 2020年2月13日
 */
public class IssueConsumableCredentialCallback extends AmopCallback {

    private static final Logger logger = LoggerFactory
        .getLogger(IssueConsumableCredentialCallback.class);
    /**
     * data driver.
     */
    private static Persistence persistence;
    private static ConfidentialPaymentExample confidentialPaymentExample;
    private static StorageExampleClient storageClient;

    static {
        confidentialPaymentExample = HiddenAssetContractLoader
            .getContractService1(ConfidentialPaymentExample.class);
        try {
            TransactionReceipt receipt = confidentialPaymentExample.enableParallel().send();
            if (!StringUtils
                .equals(receipt.getStatus(), ParamKeyConstant.TRNSACTION_RECEIPT_STATUS_SUCCESS)) {
                logger.error(
                    "[IssueConsumableCredentialCallback] load confidential Payment contract failed.");
            }
            storageClient = new StorageExampleClient(confidentialPaymentExample,
                "confidential_payment", "regulation_info");
            storageClient.init();
        } catch (Exception e) {
            logger.error(
                "[IssueConsumableCredentialCallback] load confidential Payment contract with exception.");
        }

    }

    private CredentialPojoService credentialService = new CredentialPojoServiceImpl();
    private CallbackService callbackService;

    private static Persistence getDataDriver() {

        if (persistence == null) {
            persistence = new MysqlDriver();
        }
        return persistence;
    }

    @Override
    public RequestConsumableCredentialResponse onPush(RequestConsumableCredentialArgs args) {

        RequestConsumableCredentialResponse result = new RequestConsumableCredentialResponse();

        //1. 根据seqId取出关联的policyId,targetWeid,challenge，进行presentation的验证
        String seqId = args.getSeqId();
        ResponseData<String> dbResp = getDataDriver()
            .get(DataDriverConstant.DOMAIN_CONSUMABLE_CREDENTIAL, seqId);
        if (dbResp.getErrorCode() != ErrorCode.SUCCESS.getCode()) {
            return result;
        }

        String data = dbResp.getResult();
        Map<String, String> mapData = DataToolUtils.deserialize(data, HashMap.class);
        String policyId = mapData.get(ParamKeyConstant.POLICY_ID);
        String challengeJson = mapData.get(ParamKeyConstant.CHALLENGE);
        Challenge challenge = Challenge.fromJson(challengeJson);
        String targetWeId = mapData.get(ParamKeyConstant.TARGET_WEID);
        //String credentialId = mapData.get("credentialId");

        ResponseData<String> dbResp1 = getDataDriver()
            .get(DataDriverConstant.DOMAIN_CONSUMABLE_CREDENTIAL, policyId);
        String policyStr = dbResp1.getResult();
        PresentationPolicyE presentationPolicyE = PresentationPolicyE.fromJson(policyStr);

        //verify presentation
        ResponseData<Boolean> verifyResult = credentialService.verify(
            targetWeId,
            presentationPolicyE,
            challenge,
            args.getPresentation());
        if (verifyResult.getErrorCode() != ErrorCode.SUCCESS.getCode()) {
            logger.error("[onPush] Verify presentation failed.");
            return result;
        }

        //2.从presetationPolicy里的extra里获取credentialId，生成creditValue
        String credentialId = presentationPolicyE.getExtra()
            .get(ParamKeyConstant.KEY_CREDENTIAL_ID);
        CreditValue creditValue = CreditValue.newBuilder().setStringValue(credentialId).build();

        //3. 需要issuer实现和提供，CreateConsumableCredentialPojoArgs，里面包含issuer的公私钥对
        CreateConsumableCredentialPojoArgs credentialArgs = callbackService
            .getConsumableCredentialArgs(policyId, targetWeId);
        credentialArgs.setId(credentialId);

        //4. 调用WeDPR的接口，生成creditValue
        WeIdAuthentication auth = credentialArgs.getWeIdAuthentication();
        String privateKey = auth.getWeIdPrivateKey().getPrivateKey();
//        String pubKey = auth.getWeIdPublicKeyId();
        ECKeyPair keyPair = ECKeyPair.create(new BigInteger(privateKey));

        RedeemerResult redeemerResult = null;
        try {
            redeemerResult = RedeemerClient
                .confirmNonnumericalCredit(
                    keyPair,
                    args.getOwnerResult().issueArgument,
                    creditValue);
        } catch (InvalidProtocolBufferException | PkeyGenException | WedprException e) {
            logger.error("[onPush] issuer call confirmNonnumericalCredit() failed.");
        }
        String creditCredential = redeemerResult.creditCredential;
        //CreateConsumableCredentialPojoArgs credentialArgs = new CreateConsumableCredentialPojoArgs();
        credentialArgs.setCreditCredential(creditCredential);

        //5.创建可消耗凭证
        ResponseData<CredentialPojo> consumableCredentialResp = credentialService
            .createConsumableCredential(credentialArgs);
        if (consumableCredentialResp.getErrorCode() != ErrorCode.SUCCESS.getCode()) {
            logger.error(
                "[RequestConsumableCredentialResponse] Create consuamable credential failed. error code is {}",
                consumableCredentialResp.getErrorCode());
            return result;
        }

        //6. 凭证信息上链
        try {
            storageClient.issueCredit(redeemerResult.issueArgument);
        } catch (Exception e) {

            logger.error(
                "[RequestConsumableCredentialResponse] storageClient issue credit failed: {}", e
            );
            return result;
        }

        //7. 将生成的凭证返回给用户，然后将凭证入库？
        result.setCredentialPojo(consumableCredentialResp.getResult());
        result.setErrorCode(ErrorCode.SUCCESS.getCode());
        return result;
    }

    public void registPolicyService(CallbackService service) {
        callbackService = service;
    }


    public static class HiddenAssetContractLoader extends BaseEngine {

        public static <T> T getContractService1(Class<T> cls) {
            return getContractService(fiscoConfig.getHiddenAssetAddress(), cls);
        }
    }
}
