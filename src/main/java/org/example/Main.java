package org.example;

import org.torusresearch.fetchnodedetails.types.TorusNetwork;
import org.torusresearch.torusutils.types.RetrieveSharesResponse;
import types.CustomAuthArgs;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Hello world!");
        CustomAuth torusSdk;
        CustomAuthArgs authArgs = new CustomAuthArgs(TorusNetwork.TESTNET);
        authArgs.setNetworkUrl("https://small-long-brook.ropsten.quiknode.pro/e2fd2eb01412e80623787d1c40094465aa67624a");

        // Initialize CustomAuth
        torusSdk = new CustomAuth(authArgs);
        String verifier = "torus-test-health";
        String verifierId = "test123@tor.us";
        HashMap<String, Object> verifierParamsHashMap = new HashMap<>();
        verifierParamsHashMap.put("verifier_id", verifierId);
        String idToken = "eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0b3J1cy1rZXktdGVzdCIsImF1ZCI6InRvcnVzLWtleS10ZXN0IiwibmFtZSI6InRlc3QxMjNAdG9yLnVzIiwiZW1haWwiOiJ0ZXN0MTIzQHRvci51cyIsInNjb3BlIjoiZW1haWwiLCJpYXQiOjE2NjYwODkxMTIsImVhdCI6MTY2NjA4OTIzMiwiZXhwIjoxNjY2MDg5MjMyfQ.ORa2ug3PDwX_AtvpzY6EvjbvyU-IkG1dWUIAoI6MIWyvkEV4J45Jlo2I-I2oAfEaoi4h1tdlqAjqUzDA7KPGmg";
        RetrieveSharesResponse retrieveSharesResponse = torusSdk.getTorusKey(verifier, verifierId, verifierParamsHashMap, idToken).get();
        System.out.println(retrieveSharesResponse.getPrivKey());
    }
}