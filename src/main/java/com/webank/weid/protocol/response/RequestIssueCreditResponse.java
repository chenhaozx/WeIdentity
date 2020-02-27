package com.webank.weid.protocol.response;

import com.webank.wedpr.confidentialpayment.RedeemerResult;
import lombok.Data;

/**
 * @author tonychen 2020年1月16日
 */
@Data
public class RequestIssueCreditResponse {

    protected String errorMessage;

    private Integer errorCode;

    private RedeemerResult redeemerResult;
}
