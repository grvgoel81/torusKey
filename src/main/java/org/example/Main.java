package org.example;

import org.torusresearch.fetchnodedetails.types.TorusNetwork;
import org.torusresearch.torusutils.types.RetrieveSharesResponse;
import types.CustomAuthArgs;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Hello world!");
        CustomAuth torusSdk;
        CustomAuthArgs authArgs = new CustomAuthArgs(TorusNetwork.TESTNET);  // Change network to mainnet in production
        // change network url to mainnnet in production.
        authArgs.setNetworkUrl("https://small-long-brook.ropsten.quiknode.pro/e2fd2eb01412e80623787d1c40094465aa67624a");

        // Initialize CustomAuth
        torusSdk = new CustomAuth(authArgs);
        String verifier = "torus-test-health";
        String verifierId = "test123@tor.us";
        HashMap<String, Object> verifierParamsHashMap = new HashMap<>();
        verifierParamsHashMap.put("verifier_id", verifierId);
        String idToken = "eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0b3J1cy1rZXktdGVzdCIsImF1ZCI6InRvcnVzLWtleS10ZXN0IiwibmFtZSI6InRlc3QxMjNAdG9yLnVzIiwiZW1haWwiOiJ0ZXN0MTIzQHRvci51cyIsInNjb3BlIjoiZW1haWwiLCJpYXQiOjE2NjYxNTQ3ODMsImVhdCI6MTY2NjE1NDkwMywiZXhwIjoxNjY2MTU0OTAzfQ.mQcFS5JU0z31wgxmQ7LgyiTPYGAg3i0peSRxSwzBnylFbAnlBM515p8KGge2lqkhRUoMh0Lo9wTbgac8RvaXhA";
        BigInteger privKey = torusSdk.getTorusKey(verifier, verifierId, verifierParamsHashMap, idToken).get();
        // hex priv key,
        // you can derive eth address with this
        // Note: Don't use this priv key once mfa is enabled.
        System.out.println(privKey.toString(16)); // TODO pad the key to 64 chars, append 0s in front if length is less than 64.
    }
}