package com.webank.weid.suite.transportService;

import com.webank.weid.constant.TransportationType;
import com.webank.weid.suite.transportService.request.intf.Transport;

/**
 * @author tonychen 2020年2月11日
 */
public abstract class TransportServiceFactory {

    public static Transport getTransportService(TransportationType type) {

        return null;
    }
}
