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

    //void registerCallback(Integer directRouteMsgType, PushNotifyAllCallback directRouteCallback);

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
