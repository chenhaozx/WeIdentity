package com.webank.weid.protocol.response;

import lombok.Data;

import com.webank.weid.protocol.base.CredentialPojo;

/**
 * @author tonychen 2020年2月6日
 */
@Data
public class RequestConsumableCredentialResponse {

    private Integer errorCode;

    private String errorMessage;

    /**
     * consumable credential
     */
    private CredentialPojo credentialPojo;
}
