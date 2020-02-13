package com.webank.weid.suite.transportService.request.intf;

import com.webank.weid.protocol.amop.RequestConsumableCredentialArgs;
import com.webank.weid.protocol.amop.RequestVerifierOwnerResultArgs;
import com.webank.weid.protocol.amop.RequestZkpConsumableCredentialArgs;
import com.webank.weid.protocol.amop.TransferCreditCredentialArgs;
import com.webank.weid.protocol.base.PolicyAndChallenge;
import com.webank.weid.protocol.response.RequestConsumableCredentialResponse;
import com.webank.weid.protocol.response.RequestVerifierOwnerResultResponse;
import com.webank.weid.protocol.response.RequestZkpConsumableCredentialResponse;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.protocol.response.TransferCreditCredentialResponse;

/**
 * 传输层的抽象接口，各个类型的传输载体实现该接口
 *
 * @author tonychen 2020年2月6日
 */
public interface Transport {

    /**
     * 获取policyAndChallenge
     */
    ResponseData<PolicyAndChallenge> getPolicyAndChallenge(
        String orgId,
        Integer policyId,
        String targetUserWeId
    );

    /**
     * request issuer to issue consumable credential.
     *
     * @param toOrgId the id of the target organization
     * @param args including presentation and ownerResult.
     * @return redeemerResult
     */
    ResponseData<RequestConsumableCredentialResponse> requestOriginalConsumableCredential(
        String toOrgId,
        RequestConsumableCredentialArgs args
    );

    /**
     * request issuer to issue consumable credential.
     *
     * @param toOrgId the id of the target organization
     * @param args including presentation and ownerResult.
     * @return redeemerResult
     */
    ResponseData<RequestZkpConsumableCredentialResponse> requestZkpConsumableCredential(
        String toOrgId,
        RequestZkpConsumableCredentialArgs args
    );

    /**
     * request verifier to generate ownerResult.
     *
     * @param toOrgId the id of the target organization
     * @param args including presentation and ownerResult.
     * @return ownerResult
     */
    ResponseData<RequestVerifierOwnerResultResponse> requestVerifierOwnerResult(
        String toOrgId,
        RequestVerifierOwnerResultArgs args
    );

    /**
     * transfer credit credential to verifier.
     *
     * @param toOrgId the id of the target organization
     * @param args including presentation and ownerResult.
     * @return status
     */
    ResponseData<TransferCreditCredentialResponse> transferCreditCredential(
        String toOrgId,
        TransferCreditCredentialArgs args
    );
}
