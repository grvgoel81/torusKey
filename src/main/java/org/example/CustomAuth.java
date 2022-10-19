package org.example;

import org.torusresearch.fetchnodedetails.FetchNodeDetails;
import org.torusresearch.fetchnodedetails.types.NodeDetails;
import org.torusresearch.torusutils.TorusUtils;
import org.torusresearch.torusutils.helpers.Utils;
import org.torusresearch.torusutils.types.RetrieveSharesResponse;
import org.torusresearch.torusutils.types.TorusCtorOptions;
import org.torusresearch.torusutils.types.TorusPublicKey;
import org.torusresearch.torusutils.types.VerifierArgs;
import types.CustomAuthArgs;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CustomAuth {
    public final FetchNodeDetails nodeDetailManager;
    public final TorusUtils torusUtils;
    private final CustomAuthArgs customAuthArgs;

    public CustomAuth(CustomAuthArgs _customAuthArgs) {
        this.customAuthArgs = _customAuthArgs;
        if (Utils.isEmpty(_customAuthArgs.getNetworkUrl())) {
            this.nodeDetailManager = new FetchNodeDetails(_customAuthArgs.getNetwork(),
                    CustomAuthArgs.CONTRACT_MAP.get(_customAuthArgs.getNetwork()));
        } else {
            this.nodeDetailManager = new FetchNodeDetails(_customAuthArgs.getNetworkUrl(),
                    CustomAuthArgs.CONTRACT_MAP.get(_customAuthArgs.getNetwork()));
        }

        TorusCtorOptions opts = new TorusCtorOptions("Web3Auth");
        opts.setEnableOneKey(true);
        opts.setNetwork(_customAuthArgs.getNetwork().toString());
        opts.setSignerHost(CustomAuthArgs.SIGNER_MAP.get(_customAuthArgs.getNetwork()) + "/api/sign");
        opts.setAllowHost(CustomAuthArgs.SIGNER_MAP.get(_customAuthArgs.getNetwork()) + "/api/allow");
        this.torusUtils = new TorusUtils(opts);
    }

    public CompletableFuture<BigInteger> getTorusKey(String verifier, String verifierId, HashMap<String, Object> verifierParams,
                                                                 String idToken) throws ExecutionException, InterruptedException {
        NodeDetails nodeDetails = this.nodeDetailManager.getNodeDetails(verifier, verifierId).get();
        // this function creates a wallet if not doesn't exists
        TorusPublicKey torusPublicKey = torusUtils.getPublicAddress(nodeDetails.getTorusNodeEndpoints(), nodeDetails.getTorusNodePub(), new VerifierArgs(verifier, verifierId)).get();
        RetrieveSharesResponse shareResponse = this.torusUtils.retrieveShares(nodeDetails.getTorusNodeEndpoints(), nodeDetails.getTorusIndexes(),verifier, verifierParams, idToken).get();
        CompletableFuture<BigInteger> response = new CompletableFuture<BigInteger>();
        if (shareResponse == null) {
            response.completeExceptionally(new Exception("Invalid Share response"));
        } else if (!shareResponse.getEthAddress().equalsIgnoreCase(torusPublicKey.getAddress())) {
            response.completeExceptionally(new Exception("Share response doesn't match public key response"));
        } else {
            response.complete(shareResponse.getPrivKey());
        }
        return response;
    }
}
