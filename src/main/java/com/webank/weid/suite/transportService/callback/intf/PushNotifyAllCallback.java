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

package com.webank.weid.suite.transportService.callback.intf;

import com.webank.weid.protocol.amop.CheckAmopMsgHealthArgs;
import com.webank.weid.protocol.amop.GetEncryptKeyArgs;
import com.webank.weid.protocol.amop.GetPolicyAndChallengeArgs;
import com.webank.weid.protocol.amop.GetPolicyAndPreCredentialArgs;
import com.webank.weid.protocol.amop.RequestConsumableCredentialArgs;
import com.webank.weid.protocol.amop.RequestVerifierOwnerResultArgs;
import com.webank.weid.protocol.amop.TransferCreditCredentialArgs;
import com.webank.weid.protocol.response.AmopNotifyMsgResult;
import com.webank.weid.protocol.response.AmopResponse;
import com.webank.weid.protocol.response.GetEncryptKeyResponse;
import com.webank.weid.protocol.response.GetPolicyAndChallengeResponse;
import com.webank.weid.protocol.response.PolicyAndPreCredentialResponse;
import com.webank.weid.protocol.response.RequestConsumableCredentialResponse;
import com.webank.weid.protocol.response.RequestVerifierOwnerResultResponse;
import com.webank.weid.protocol.response.TransferCreditCredentialResponse;
import com.webank.weid.service.impl.base.AmopCommonArgs;

/**
 * Created by junqizhang on 17/5/24.
 */
public interface PushNotifyAllCallback {


    /**
     * 链上链下health check, 不需要覆盖实现.
     *
     * @param arg echo arg
     * @return amopNotifyMsgResult
     */
    AmopNotifyMsgResult onPush(CheckAmopMsgHealthArgs arg);


    /**
     * 默认针对TYPE_TRANSPORTATION消息的回调处理.
     *
     * @param arg AMOP请求参数
     * @return AMOP相应体
     */
    public AmopResponse onPush(AmopCommonArgs arg);


    /**
     * 默认获取秘钥的回调处理.
     *
     * @param arg 获取秘钥需要的参数
     * @return 返回秘钥的响应体
     */
    public GetEncryptKeyResponse onPush(GetEncryptKeyArgs arg);


    /**
     * 默认获取PolicyAndChallenge的回调处理.
     *
     * @param arg 获取PolicyAndChallenge需要的参数
     * @return 返回PolicyAndChallenge的响应体
     */
    public GetPolicyAndChallengeResponse onPush(GetPolicyAndChallengeArgs arg);

    /**
     * 默认获取PolicyAndChallenge的回调处理.
     *
     * @param args 获取PolicyAndChallenge需要的参数
     * @return 返回PolicyAndChallenge的响应体
     */
    public PolicyAndPreCredentialResponse onPush(GetPolicyAndPreCredentialArgs args);

    /**
     * request issuer to issue consumable credential.
     *
     * @param toOrgId the id of the target organization
     * @param args including presentation and ownerResult.
     * @return redeemerResult
     */
    public RequestConsumableCredentialResponse onPush(
        RequestConsumableCredentialArgs args
    );

    /**
     * request verifier to generate ownerResult.
     *
     * @param toOrgId the id of the target organization
     * @param args including presentation and ownerResult.
     * @return ownerResult
     */
    RequestVerifierOwnerResultResponse onPush(
        RequestVerifierOwnerResultArgs args
    );

    /**
     * transfer credit credential to verifier.
     *
     * @param toOrgId the id of the target organization
     * @param args including presentation and ownerResult.
     * @return status
     */
    TransferCreditCredentialResponse onPush(
        TransferCreditCredentialArgs args
    );

}
