package com.webank.weid.service.impl.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webank.weid.constant.CredentialType;
import com.webank.weid.constant.JsonSchemaConstant;
import com.webank.weid.constant.TransportationType;
import com.webank.weid.protocol.base.Cpt;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.PolicyAndChallenge;
import com.webank.weid.protocol.base.WeIdAuthentication;
import com.webank.weid.protocol.base.WeIdPrivateKey;
import com.webank.weid.protocol.request.CptMapArgs;
import com.webank.weid.protocol.request.CreateCredentialPojoArgs;
import com.webank.weid.protocol.request.GetConsumableCredentialArgs;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.CptService;
import com.webank.weid.rpc.CredentialPojoService;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.CptServiceImpl;
import com.webank.weid.service.impl.CredentialPojoServiceImpl;
import com.webank.weid.service.impl.WeIdServiceImpl;
import com.webank.weid.suite.transportService.TransportServiceFactory;
import com.webank.weid.suite.transportService.request.intf.Transport;

/**
 * @author tonychen 2020年2月17日
 *
 */
public class TestClient {

	private static CptService cptService = new CptServiceImpl();
	
	private static WeIdService weidService = new WeIdServiceImpl();
	private static CredentialPojoService credentialService = new CredentialPojoServiceImpl();
	
	private static String weid = "did:weid:1:0xe7b37bffd9fbd0d9cc9633df535201e4b564f599";
	private static String privateKey = "7746600593388208617857642548075333875372990506954729242323629237212168998126";
	private static String pubKey = "5529938847924189901909857450811468966122135576505094212936205590408186790039977482949885584558521257322529169981943965037510288592363647983864823584055760";
	
	private static String userWeid = "did:weid:1:0xb51ab047cd6cbb6211121d3af1233edf24a55e87";
	private static String userPrivateKey = "3089145099924155945356858169377432556789848726839044842511744587751445246036";
	private static String userPubKey = "1882955307520954441993351149711414032395058519844050740451530118241022183094839997582616091290459955761171321631997742932030615839655956021434654674190424";
	
	private static PolicyAndChallenge policyAndChallenge;
	
	private static CredentialPojo credentialPojo;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		ResponseData<CreateWeIdDataResult> weidResp = weidService.createWeId();
//		System.out.println(weidResp);
//		if(true) {
//			return;
//		}
		
		registerCpt();
		policyAndChallenge = getPolicyAndChallenge();
		System.out.println(policyAndChallenge.toJson());
		
		testGetConsumableCredential();
	}
	
	private static void registerCpt() {
		CptMapArgs cptMapArgs = new CptMapArgs();
		WeIdAuthentication weIdAuthentication = new WeIdAuthentication();
		weIdAuthentication.setWeId(weid);
		weIdAuthentication.setWeIdPublicKeyId(pubKey);
		WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
		weIdPrivateKey.setPrivateKey(privateKey);
		weIdAuthentication.setWeIdPrivateKey(weIdPrivateKey);
		
//		cptMapArgs.setWeIdAuthentication(weIdAuthentication);
//		ResponseData<CreateWeIdDataResult> resp = weidService.createWeId();
//		System.out.println(resp);
		cptMapArgs.setWeIdAuthentication(weIdAuthentication);
		HashMap<String, Object> cptJsonSchema = buildCptJsonSchema();
//        cptJsonSchema.put("标题", "cpt template");
//        cptJsonSchema.put("描述", "this is a cpt template");
//
//        HashMap<String, Object> propertitesMap1 = new HashMap<String, Object>(2);
//        propertitesMap1.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
//        propertitesMap1.put("描述", "this is name");
//
//        String[] genderEnum = {"女性", "男性"};
//        HashMap<String, Object> propertitesMap2 = new HashMap<String, Object>(2);
//        propertitesMap2.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
//        propertitesMap2.put(JsonSchemaConstant.DATA_TYPE_ENUM, genderEnum);
//
//        HashMap<String, Object> propertitesMap3 = new HashMap<String, Object>(2);
//        propertitesMap3.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_NUMBER);
//        propertitesMap3.put("描述", "this is age");
//
//        HashMap<String, Object> cptJsonSchemaKeys = new HashMap<String, Object>(3);
//        cptJsonSchemaKeys.put("name", propertitesMap1);
//        cptJsonSchemaKeys.put("gender", propertitesMap2);
//        cptJsonSchemaKeys.put("age", propertitesMap3);
//        cptJsonSchema.put(JsonSchemaConstant.PROPERTIES_KEY, cptJsonSchemaKeys);
//
//        String[] genderRequired = {"name", "gender"};
//        cptJsonSchema.put(JsonSchemaConstant.REQUIRED_KEY, genderRequired);
		cptMapArgs.setCptJsonSchema(cptJsonSchema);
		//cptMapArgs.setCptType(CptType.ZKP);
		//ResponseData<CptBaseInfo>cptres = cptService.registerCpt(cptMapArgs);
		//System.out.println(cptres);
//		if(true) {
//			return;
//		}
		ResponseData<Cpt>cptres = cptService.queryCpt(2000002);
		System.out.println(cptres);
		
//		credentialService
		CreateCredentialPojoArgs<Map> credentialArgs = new CreateCredentialPojoArgs();
		Map<String, Object>claimMap = new HashMap<>();
		claimMap.put("age", 18);
		claimMap.put("gender", "F");
		claimMap.put("name", "zhangsan");
		claimMap.put("weid", userWeid);
		
		credentialArgs.setClaim(claimMap);
		credentialArgs.setCptId(2000002);
		credentialArgs.setWeIdAuthentication(weIdAuthentication);
		credentialArgs.setIssuer(weid);
		credentialArgs.setExpirationDate(System.currentTimeMillis()+100000000);
		
		ResponseData<CredentialPojo> credentialResp = credentialService.createCredential(credentialArgs);
		credentialPojo = credentialResp.getResult();
		System.out.println(credentialResp);
		
		ResponseData<Boolean> verifyResult = credentialService.verify(weid, credentialResp.getResult());
		System.out.println(verifyResult);
	}

	 public static HashMap<String, Object> buildCptJsonSchema() {

	        HashMap<String, Object> cptJsonSchemaNew = new HashMap<String, Object>(3);
	        cptJsonSchemaNew.put(JsonSchemaConstant.TITLE_KEY, "Digital Identity");
	        cptJsonSchemaNew.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is a cpt template");

	        HashMap<String, Object> propertitesMap1 = new HashMap<String, Object>(2);
	        propertitesMap1.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
	        propertitesMap1.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is name");

	        String[] genderEnum = {"F", "M"};
	        HashMap<String, Object> propertitesMap2 = new HashMap<String, Object>(2);
	        propertitesMap2.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
	        propertitesMap2.put(JsonSchemaConstant.DATA_TYPE_ENUM, genderEnum);

	        HashMap<String, Object> propertitesMap3 = new HashMap<String, Object>(2);
	        propertitesMap3.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_NUMBER);
	        propertitesMap3.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is age");

	        HashMap<String, Object> propertitesMap4 = new HashMap<String, Object>(2);
	        propertitesMap4.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
	        propertitesMap4.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is weid");

	        HashMap<String, Object> cptJsonSchema = new HashMap<String, Object>(3);
	        cptJsonSchema.put("name", propertitesMap1);
	        cptJsonSchema.put("gender", propertitesMap2);
	        cptJsonSchema.put("age", propertitesMap3);
	        cptJsonSchema.put("weid", propertitesMap4);
	        cptJsonSchemaNew.put(JsonSchemaConstant.PROPERTIES_KEY, cptJsonSchema);

	        String[] genderRequired = {"weid", "name", "gender"};
	        cptJsonSchemaNew.put(JsonSchemaConstant.REQUIRED_KEY, genderRequired);

	        return cptJsonSchemaNew;
	    }
	private static PolicyAndChallenge getPolicyAndChallenge() {
		
		Transport transport = TransportServiceFactory.getTransportService(TransportationType.AMOP);
		ResponseData<PolicyAndChallenge> policyResp = transport.getPolicyAndChallenge("aaa", 12345, userWeid);
		PolicyAndChallenge policyAndChallenge = policyResp.getResult();
		return policyAndChallenge;
	}
	
	private static CredentialPojo testGetConsumableCredential() {
		
		//CreateConsumableCredentialPojoArgs args = new CreateConsumableCredentialPojoArgs();
		//credentialService.createConsumableCredential(args);
		GetConsumableCredentialArgs args = new GetConsumableCredentialArgs();
		List<CredentialPojo>credentialPojoList = new ArrayList<CredentialPojo>();
		credentialPojoList.add(credentialPojo);
		args.setCredentialPojoList(credentialPojoList);
		args.setPolicyAndChallenge(policyAndChallenge);
		args.setToOrgId("aaa");
		args.setTransportationType(TransportationType.AMOP);
		args.setType(CredentialType.ORIGINAL);
		WeIdAuthentication userAuth = new WeIdAuthentication();
		userAuth.setWeId(userWeid);
		WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
		weIdPrivateKey.setPrivateKey(userPrivateKey);
		userAuth.setWeIdPrivateKey(weIdPrivateKey);
		userAuth.setWeIdPublicKeyId(userPubKey);
		ResponseData<CredentialPojo> credentialResp = credentialService.getConsumableCredential(args, userAuth);
		CredentialPojo credential = credentialResp.getResult();
		System.out.println(credentialResp);
		return credential;
	}
}
