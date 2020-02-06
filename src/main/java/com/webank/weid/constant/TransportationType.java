package com.webank.weid.constant;

import lombok.Getter;

/**
 * @author tonychen 2020年2月6日
 *
 */
@Getter
public enum TransportationType {

	AMOP(0),
	HTTP(1),
	QRCode(2);
	
	private Integer code;
	
	private TransportationType(Integer typeCode) {
	
		this.code = typeCode;
	}
}
