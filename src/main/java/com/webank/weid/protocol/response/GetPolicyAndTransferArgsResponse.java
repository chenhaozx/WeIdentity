package com.webank.weid.protocol.response;

import com.webank.weid.protocol.base.PolicyAndChallenge;

import lombok.Data;

/**
 * @author tonychen 2020年1月16日
 *
 */
@Data
public class GetPolicyAndTransferArgsResponse {


    /**
     * policy And Challenge for user.
     */
    private PolicyAndChallenge policyAndChallenge;

    /**
     * transferArgument.
     */
    private String transferArgument;
    
    /**
     * return errorCode.
     */
    private Integer errorCode;

    /**
     * return errorMessage.
     */
    protected String errorMessage;
}
