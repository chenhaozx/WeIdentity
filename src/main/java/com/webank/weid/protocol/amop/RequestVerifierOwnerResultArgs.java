package com.webank.weid.protocol.amop;

import lombok.Data;

import com.webank.weid.protocol.base.PresentationE;

/**
 * @author tonychen 2020年2月6日
 */
@Data
public class RequestVerifierOwnerResultArgs {

    /**
     * user's credential list.
     */
    private PresentationE presentation;
}
