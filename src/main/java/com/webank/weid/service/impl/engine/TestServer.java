package com.webank.weid.service.impl.engine;

import com.webank.weid.suite.transportService.callback.impl.amop.CallbackServiceDemo;

/**
 * @author tonychen 2020年2月25日
 *
 */
public class TestServer {

	/**
	 * @param args
	 */
	private static String weid = "did:weid:1:0xe7b37bffd9fbd0d9cc9633df535201e4b564f599";
	private static String privateKey = "7746600593388208617857642548075333875372990506954729242323629237212168998126";
	private static String pubKey = "5529938847924189901909857450811468966122135576505094212936205590408186790039977482949885584558521257322529169981943965037510288592363647983864823584055760";
	
	public static void main(String[] args) {

		System.out.println("Server start.......");
		CallbackServiceDemo demo = new CallbackServiceDemo();
		
	}

}
