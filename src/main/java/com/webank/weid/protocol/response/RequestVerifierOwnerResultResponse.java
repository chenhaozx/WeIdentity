package com.webank.weid.protocol.response;

import com.webank.wedpr.confidentialpayment.OwnerResult;
import lombok.Data;

/**
 * @author tonychen 2020年2月6日
 */
@Data
public class RequestVerifierOwnerResultResponse {

    private int errorCode;

    private String errorMessage;

    /**
     * verifier's middle data.
     */
    private OwnerResult ownerResult;
}
