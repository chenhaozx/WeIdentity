package com.webank.weid.suite.transportService.request.impl;

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
import com.webank.weid.suite.transportService.request.intf.Transport;

/**
 * @author tonychen 2020年2月11日
 */
public class AmopTransportService implements Transport {

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.request.intf.Transport#getPolicyAndChallenge(java.lang.String, java.lang.Integer, java.lang.String)
     */
    @Override
    public ResponseData<PolicyAndChallenge> getPolicyAndChallenge(String orgId, Integer policyId,
        String targetUserWeId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.request.intf.Transport#requestOriginalConsumableCredential(java.lang.String, com.webank.weid.protocol.amop.RequestConsumableCredentialArgs)
     */
    @Override
    public ResponseData<RequestConsumableCredentialResponse> requestOriginalConsumableCredential(
        String toOrgId,
        RequestConsumableCredentialArgs args) {

        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.request.intf.Transport#transferCreditCredential(java.lang.String, com.webank.weid.protocol.amop.TransferCreditCredentialArgs)
     */
    @Override
    public ResponseData<TransferCreditCredentialResponse> transferCreditCredential(String toOrgId,
        TransferCreditCredentialArgs args) {
        // TODO Auto-generated method stub
        return null;
    }

}
