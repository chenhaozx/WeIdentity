package com.webank.weid.protocol.response;

import com.webank.weid.protocol.base.CredentialPojo;

import lombok.Data;

/**
 * @author tonychen 2020年2月12日
 */
@Data
public class RequestZkpConsumableCredentialResponse {

    private Integer errorCode;

    private String errorMessage;

    /**
     * consumable credential
     */
    private CredentialPojo credentialPojo;
}
