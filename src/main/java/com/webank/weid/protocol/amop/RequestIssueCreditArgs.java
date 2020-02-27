package com.webank.weid.protocol.amop;

import com.webank.wedpr.confidentialpayment.OwnerResult;
import lombok.Data;

import com.webank.weid.protocol.base.PresentationE;

/**
 * @author tonychen 2020年1月16日
 */
@Data
public class RequestIssueCreditArgs {

    /**
     * credential list.
     */
    private PresentationE presentation;

    /**
     * user's ownerResult, generated from user's secret key
     */
    private OwnerResult ownerResult;
}
