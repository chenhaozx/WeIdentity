/*
 *       Copyright© (2018) WeBank Co., Ltd.
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

package com.webank.weid.rpc;

import java.util.List;

import com.webank.weid.protocol.base.Challenge;
import com.webank.weid.protocol.base.ClaimPolicy;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.PresentationE;
import com.webank.weid.protocol.base.PresentationPolicyE;
import com.webank.weid.protocol.base.WeIdAuthentication;
import com.webank.weid.protocol.base.WeIdPublicKey;
import com.webank.weid.protocol.request.CreateConsumableCredentialPojoArgs;
import com.webank.weid.protocol.request.CreateCredentialPojoArgs;
import com.webank.weid.protocol.request.CreateZkpCredentialPojoArgs;
import com.webank.weid.protocol.request.GetConsumableCredentialArgs;
import com.webank.weid.protocol.request.TransferConsumableCredentialArgs;
import com.webank.weid.protocol.response.ResponseData;

/**
 * Service inf for operations on Credentials.
 *
 * @author tonychen
 */
public interface CredentialPojoService {

    //ResponseData<PolicyAndChallenge> getPolicyAndChallenge(
    //String orgId,
    //Integer policyId,
    //String targetUserWeId
    //);

    /**
     * Generate a credential for full claim content.
     *
     * @param args the args
     * @return CredentialPojo
     */
    ResponseData<CredentialPojo> createCredential(CreateCredentialPojoArgs args);

    /**
     * Generate a consumable credential for full claim content.
     *
     * @param args the args
     * @return CredentialPojo
     */
    ResponseData<CredentialPojo> createConsumableCredential(
        CreateConsumableCredentialPojoArgs args);

    /**
     * Generate a zkp credential for full claim content.
     *
     * @param args the args
     * @return CredentialPojo
     */
    ResponseData<CredentialPojo> createZkpCredential(CreateZkpCredentialPojoArgs args);

    /**
     * user make credential from issuer's pre-credential.
     *
     * @param preCredential issuer's pre-credential
     * @param claimJson user claim
     * @param weIdAuthentication auth
     * @return credential based on CPT 111
     */
    ResponseData<CredentialPojo> prepareZkpCredential(
        CredentialPojo preCredential,
        String claimJson,
        WeIdAuthentication weIdAuthentication
    );

    /**
     * Generate a selective disclosure credential with specified claim policy.
     *
     * @param credential the credential
     * @param claimPolicy describe which fields in credential should be disclosed.
     * @return CredentialPojo
     */
    ResponseData<CredentialPojo> createSelectiveCredential(
        CredentialPojo credential,
        ClaimPolicy claimPolicy
    );

    /**
     * Add an extra signer and signature to a Credential. Multiple signatures will be appended in an
     * embedded manner.
     *
     * @param credentialList original credential list
     * @param callerAuth the passed-in privateKey and WeID bundle to sign
     * @return the modified CredentialWrapper
     */
    ResponseData<CredentialPojo> addSignature(
        List<CredentialPojo> credentialList,
        WeIdAuthentication callerAuth);

    /**
     * Get the full hash value of a CredentialPojo. All fields in the CredentialPojo will be
     * included. This method should be called when creating and verifying the Credential Evidence
     * and the result is selectively-disclosure irrelevant.
     *
     * @param credentialPojo the args
     * @return the Credential Hash value
     */
    ResponseData<String> getCredentialPojoHash(CredentialPojo credentialPojo);

    /**
     * user generate random secret key by user's private key.
     *
     * @param randomStr a random string to generate secret key
     * @param weIdAuthentication user's authentication info
     * @return ownerResult generated from user's secret
     */
    ResponseData<String> prepareCredit(
        String randomStr,
        WeIdAuthentication weIdAuthentication
    );

    /**
     * request issuer to issue a credential.
     *
     * @param args parameters of requesting issuer to issue a credential
     * @param weIdAuthentication user's authentication info
     * @return consumable credential
     */
    ResponseData<CredentialPojo> getConsumableCredential(
        GetConsumableCredentialArgs args,
        WeIdAuthentication weIdAuthentication
    );

    /**
     * user transfer consumable credentialPojo to verifier.
     *
     * @param args parameters of transferring consumable credential to verifier.
     * @param weIdAuthentication user's authentication info
     * @return status
     */
    ResponseData<Integer> transferConsumableCredential(
        TransferConsumableCredentialArgs args,
        WeIdAuthentication weIdAuthentication
    );


    /**
     * consume a consumable credential. Public key will be fetched from chain.
     *
     * @param issuerWeId the issuer WeId
     * @param credential the consumable credential
     * @return the verification result. True if yes, false otherwise with exact verify error codes
     */
    ResponseData<Boolean> consume(
        String issuerWeId,
        CredentialPojo consumableCredential,
        WeIdAuthentication weIdAuthentication
    );

    /**
     * Verify the validity of a credential. Public key will be fetched from chain.
     *
     * @param issuerWeId the issuer WeId
     * @param credential the credential
     * @return the verification result. True if yes, false otherwise with exact verify error codes
     */
    ResponseData<Boolean> verify(String issuerWeId, CredentialPojo credential);

    /**
     * Verify the validity of a credential. Public key must be provided.
     *
     * @param issuerPublicKey the specified public key which used to verify credential signature
     * @param credential the credential
     * @return the verification result. True if yes, false otherwise with exact verify error codes
     */
    ResponseData<Boolean> verify(WeIdPublicKey issuerPublicKey, CredentialPojo credential);

    /**
     * verify the presentation with presenter's weid and policy.
     *
     * @param presenterWeId the presenter's weid
     * @param presentationPolicyE policy of the presentation
     * @param challenge challenge
     * @param presentationE the presentation
     * @return the verification result. True if yes, false otherwise with exact verify error codes
     */
    ResponseData<Boolean> verify(
        String presenterWeId,
        PresentationPolicyE presentationPolicyE,
        Challenge challenge,
        PresentationE presentationE
    );

    /**
     * packing according to original vouchers and disclosure strategies.
     *
     * @param credentialList original credential list
     * @param presentationPolicyE the disclosure strategies.
     * @param challenge used for authentication
     * @param weIdAuthentication owner information
     * @return PresentationE presentationE
     */
    ResponseData<PresentationE> createPresentation(
        List<CredentialPojo> credentialList,
        PresentationPolicyE presentationPolicyE,
        Challenge challenge,
        WeIdAuthentication weIdAuthentication
    );

    /**
     * Create a trusted timestamp credential.
     *
     * @param credentialList the credentialPojo list to be signed
     * @param weIdAuthentication caller authentication
     * @return the embedded timestamp in credentialPojo
     */
    ResponseData<CredentialPojo> createTrustedTimestamp(
        List<CredentialPojo> credentialList,
        WeIdAuthentication weIdAuthentication
    );
}

