package org.example;

import org.torusresearch.fetchnodedetails.types.TorusNetwork;
import types.CustomAuthArgs;
import types.TorusKey;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Hello world!");
        CustomAuth torusSdk;
        CustomAuthArgs authArgs = new CustomAuthArgs("https://scripts.toruswallet.io/redirect.html",
                TorusNetwork.TESTNET,
                "torusapp://org.torusresearch.customauthandroid/redirect");
        authArgs.setNetworkUrl("https://rpc.ankr.com/eth_ropsten");

        // Initialize CustomAuth
        torusSdk = new CustomAuth(authArgs);
        String verifier = "google-lrc";
        String verifierId = "hello@tor.us";
        HashMap<String, Object> verifierParamsHashMap = new HashMap<>();
        verifierParamsHashMap.put("verifier_id", verifierId);
        String idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijk1NTEwNGEzN2ZhOTAzZWQ4MGM1NzE0NWVjOWU4M2VkYjI5YjBjNDUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyMjE4OTg2MDk3MDktb2JmbjNwNjM3NDFsNTMzMzA5MzQzMGozcWVpaW5hYTguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyMjE4OTg2MDk3MDktb2JmbjNwNjM3NDFsNTMzMzA5MzQzMGozcWVpaW5hYTguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDM0MjkxNzk1MzA1NjU4MzkwNjIiLCJlbWFpbCI6ImdydmdvZWwxOUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6Imx4bmhHYndsVE1qaHZaSV9vT3RfUEEiLCJub25jZSI6IjRkYWY2MGY0LTQ1YjAtNDcwNy1iMWUzLTRkOGFjNzM3OWVhYSIsIm5hbWUiOiJHYXVyYXYgR29lbCIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BTG01d3UxWjNXVDEzSU9abm9YVHJkMWlKaTJjaHh5WEoxZWhVMENlUFF1Qj1zOTYtYyIsImdpdmVuX25hbWUiOiJHYXVyYXYiLCJmYW1pbHlfbmFtZSI6IkdvZWwiLCJsb2NhbGUiOiJlbiIsImlhdCI6MTY2NjAyMDAwMSwiZXhwIjoxNjY2MDIzNjAxLCJqdGkiOiJiYmZkNmE2MjYxZjJhNzRhMzcxNGY4NzY4YjgwMDZkZTQxZTFjOGNlIn0.XCFpuAFLvBsBo0_lVzncnOtAOT8OZMSCBSRp7XkmVlltrHEss3TO-kiR1Zq-Z1SgzUX5fFn9QsSguVr3XwZt4iH80Et-V5BGjAPSpzoTXeee4WAQnsWH4Kde7PyEFWSyy9oc_PlhdSruer_5wzB0CzvIUM6BeVUgfQJMfG3WfXv54K_DqNgnxfMmOhqCqg3Q7Fp6UHyygCGi9upl7yW7rwBVP3IcCn8mcNaoXt5XHWBUQnYoVYXdEG2kqObNv-tb1kdhkFDP0tYxY54bmzChy_HqnV27JlKkvfCIEWk1puL_7F3VCmq7p6IvH_yV_JMdl8TcJVLeAld7_dSszS2Flw";
        TorusKey torusKey = torusSdk.getTorusKey(verifier, verifierId, verifierParamsHashMap, idToken).get();
        System.out.println(torusKey.getPrivateKey());
    }
}