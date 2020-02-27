/*
 *       Copyright© (2018-2019) WeBank Co., Ltd.
 *
 *       This file is part of weid-java-sdk.
 *
 *       weid-java-sdk is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       weid-java-sdk is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with weid-java-sdk.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.webank.weid.suite.transportService.callback.impl.amop;

import java.util.HashMap;
import java.util.Map;

import com.webank.weid.constant.AmopMsgType;
import com.webank.weid.constant.CredentialType;
import com.webank.weid.protocol.base.Challenge;
import com.webank.weid.protocol.base.PolicyAndChallenge;
import com.webank.weid.protocol.base.PresentationPolicyE;
import com.webank.weid.protocol.base.WeIdAuthentication;
import com.webank.weid.protocol.base.WeIdPrivateKey;
import com.webank.weid.protocol.request.CreateConsumableCredentialPojoArgs;
import com.webank.weid.rpc.AmopService;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.AmopServiceImpl;
import com.webank.weid.service.impl.WeIdServiceImpl;

public class CallbackServiceDemo extends CallbackService {

    private static PresentationCallback presentationCallback = new PresentationCallback();
    private static IssueConsumableCredentialCallback consumableCallback = new IssueConsumableCredentialCallback();
    private static String issuerWeid = "did:weid:1:0xe7b37bffd9fbd0d9cc9633df535201e4b564f599";
    private static String issuerPrivateKey = "7746600593388208617857642548075333875372990506954729242323629237212168998126";
    private static String issuerPubKey = "5529938847924189901909857450811468966122135576505094212936205590408186790039977482949885584558521257322529169981943965037510288592363647983864823584055760";
    private static String userWeid = "did:weid:1:0xb51ab047cd6cbb6211121d3af1233edf24a55e87";
    private static String userPrivateKey = "3089145099924155945356858169377432556789848726839044842511744587751445246036";
    private static String userPubKey = "1882955307520954441993351149711414032395058519844050740451530118241022183094839997582616091290459955761171321631997742932030615839655956021434654674190424";
    protected AmopService amopService = new AmopServiceImpl();
    private WeIdService weidService = new WeIdServiceImpl();

    /**
     * 无参构造器,自动注册callback.
     */
    public CallbackServiceDemo() {
        presentationCallback.registPolicyService(this);
        consumableCallback.registPolicyService(this);
        amopService.registerCallback(
            AmopMsgType.GET_POLICY_AND_CHALLENGE.getValue(),
            presentationCallback
        );
        amopService.registerCallback(
            AmopMsgType.GET_CONSUMABLE_CREDENTIAL.getValue(),
            consumableCallback
        );
    }

    /**
     * 获取PolicyAndChallenge.
     *
     * @param policyId 策略编号
     * @param targetUserWeId user WeId
     * @return 返回PresentationPolicyE对象数据
     */
    public PolicyAndChallenge policyAndChallengeOnPush(
        String policyId,
        String targetUserWeId
    ) {

        System.out.println("111111111111111111111 begin");
        PresentationPolicyE presentationPolicyE = PresentationPolicyE.create("policy123456.json");
        String seed = "12345";
        Challenge challenge = Challenge.create(userWeid, seed);
        PolicyAndChallenge policyAndChallenge = new PolicyAndChallenge();
        policyAndChallenge.setChallenge(challenge);
        policyAndChallenge.setPresentationPolicyE(presentationPolicyE);
        System.out
            .println("111111111111111111111 policyAndChallenge:" + policyAndChallenge.toJson());
        System.out.println("111111111111111111111 end");
        return policyAndChallenge;

    }

    /**
     * 获取PolicyAndChallenge.
     *
     * @param policyId 策略编号
     * @param targetUserWeId user WeId
     * @return 返回PresentationPolicyE对象数据
     */
    public <T> CreateConsumableCredentialPojoArgs<T> getConsumableCredentialArgs(
        String policyId,
        String targetUserWeId
    ) {
        System.out.println("2222222222222222222222222222222 begin");
        CreateConsumableCredentialPojoArgs args = new CreateConsumableCredentialPojoArgs();

        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("age", 18);
        claimMap.put("gender", "F");
        claimMap.put("name", "zhangsan");
        claimMap.put("weid", userWeid);
        args.setClaim(claimMap);
        args.setCptId(2000002);
        args.setExpirationDate(System.currentTimeMillis() + 3000 * 24 * 60 * 60 * 1000);
        args.setIssuanceDate(System.currentTimeMillis());

//    	ResponseData<CreateWeIdDataResult> weidResp = weidService.createWeId();
//    	CreateWeIdDataResult createWeIdDataResult = weidResp.getResult();
//    	issuerWeid = createWeIdDataResult.getWeId();
//    	issuerPrivatekey = createWeIdDataResult.getUserWeIdPrivateKey().getPrivateKey();
        args.setIssuer(issuerWeid);
        args.setCredentialType(CredentialType.ORIGINAL);
        WeIdAuthentication auth = new WeIdAuthentication();
        auth.setWeId(issuerWeid);
        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(issuerPrivateKey);
        auth.setWeIdPrivateKey(weIdPrivateKey);
        auth.setWeIdPublicKeyId(issuerWeid + "#keys-0");
        args.setWeIdAuthentication(auth);

        System.out.println("2222222222222222222222222222222 end");
        return args;
    }
}
