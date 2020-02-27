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

package com.webank.weid.suite.transportService.callback;

import com.webank.weid.constant.ErrorCode;
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
import com.webank.weid.suite.transportService.callback.intf.PushNotifyAllCallback;

/**
 * @author tonychen 2020年2月12日
 */
public abstract class CommonCallback implements PushNotifyAllCallback {

    private static final String MSG_HEALTH = "I am alive!";

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.callback.intf.PushNotifyAllCallback#onPush(com.webank.weid.protocol.amop.CheckAmopMsgHealthArgs)
     */
    @Override
    public AmopNotifyMsgResult onPush(CheckAmopMsgHealthArgs arg) {

        AmopNotifyMsgResult result = new AmopNotifyMsgResult();
        result.setMessage(MSG_HEALTH);
        result.setErrorCode(ErrorCode.SUCCESS.getCode());
        result.setMessage(ErrorCode.SUCCESS.getCodeDesc());
        return result;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.callback.intf.PushNotifyAllCallback#onPush(com.webank.weid.service.impl.base.AmopCommonArgs)
     */
    @Override
    public AmopResponse onPush(AmopCommonArgs arg) {

        AmopResponse result = new AmopResponse();
        result.setErrorCode(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCode());
        result.setErrorMessage(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCodeDesc());
        return result;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.callback.intf.PushNotifyAllCallback#onPush(com.webank.weid.protocol.amop.GetEncryptKeyArgs)
     */
    @Override
    public GetEncryptKeyResponse onPush(GetEncryptKeyArgs arg) {

        GetEncryptKeyResponse result = new GetEncryptKeyResponse();
        result.setErrorCode(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCode());
        result.setErrorMessage(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCodeDesc());
        return result;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.callback.intf.PushNotifyAllCallback#onPush(com.webank.weid.protocol.amop.GetPolicyAndChallengeArgs)
     */
    @Override
    public GetPolicyAndChallengeResponse onPush(GetPolicyAndChallengeArgs arg) {

        GetPolicyAndChallengeResponse result = new GetPolicyAndChallengeResponse();
        result.setErrorCode(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCode());
        result.setErrorMessage(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCodeDesc());
        return result;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.callback.intf.PushNotifyAllCallback#onPush(com.webank.weid.protocol.amop.GetPolicyAndPreCredentialArgs)
     */
    @Override
    public PolicyAndPreCredentialResponse onPush(GetPolicyAndPreCredentialArgs args) {

        PolicyAndPreCredentialResponse result = new PolicyAndPreCredentialResponse();
        result.setErrorCode(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCode());
        result.setErrorMessage(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCodeDesc());
        return result;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.callback.intf.PushNotifyAllCallback#onPush(com.webank.weid.protocol.amop.RequestConsumableCredentialArgs)
     */
    @Override
    public RequestConsumableCredentialResponse onPush(RequestConsumableCredentialArgs args) {

        RequestConsumableCredentialResponse result = new RequestConsumableCredentialResponse();
        result.setErrorCode(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCode());
        result.setErrorMessage(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCodeDesc());
        return result;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.callback.intf.PushNotifyAllCallback#onPush(com.webank.weid.protocol.amop.RequestVerifierOwnerResultArgs)
     */
    @Override
    public RequestVerifierOwnerResultResponse onPush(RequestVerifierOwnerResultArgs args) {

        RequestVerifierOwnerResultResponse result = new RequestVerifierOwnerResultResponse();
        result.setErrorCode(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCode());
        result.setErrorMessage(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCodeDesc());
        return result;
    }

    /* (non-Javadoc)
     * @see com.webank.weid.suite.transportService.callback.intf.PushNotifyAllCallback#onPush(com.webank.weid.protocol.amop.TransferCreditCredentialArgs)
     */
    @Override
    public TransferCreditCredentialResponse onPush(TransferCreditCredentialArgs args) {

        TransferCreditCredentialResponse result = new TransferCreditCredentialResponse();
        result.setErrorCode(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCode());
        result.setErrorMessage(ErrorCode.AMOP_MSG_CALLBACK_SERVER_SIDE_NO_HANDLE.getCodeDesc());
        return result;
    }

}
