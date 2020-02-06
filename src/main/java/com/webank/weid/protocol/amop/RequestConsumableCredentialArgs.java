package com.webank.weid.protocol.amop;

import com.webank.wedpr.assethiding.OwnerResult;
import lombok.Data;

import com.webank.weid.protocol.base.PresentationE;

/**
 * @author tonychen 2020年2月6日
 */
@Data
public class RequestConsumableCredentialArgs {

    private PresentationE presentation;

    private OwnerResult ownerResult;
}
