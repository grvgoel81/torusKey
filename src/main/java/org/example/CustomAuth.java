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
import types.TorusKey;

import javax.naming.Context;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import javafx.util.Pair;

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

        TorusCtorOptions opts = new TorusCtorOptions("org.example");
        opts.setEnableOneKey(_customAuthArgs.isEnableOneKey());
        opts.setNetwork(_customAuthArgs.getNetwork().toString());
        opts.setSignerHost(CustomAuthArgs.SIGNER_MAP.get(_customAuthArgs.getNetwork()) + "/api/sign");
        opts.setAllowHost(CustomAuthArgs.SIGNER_MAP.get(_customAuthArgs.getNetwork()) + "/api/allow");
        this.torusUtils = new TorusUtils(opts);
    }

    public CompletableFuture<TorusKey> getTorusKey(String verifier, String verifierId, HashMap<String, Object> verifierParams, String idToken) {
        return this.nodeDetailManager.getNodeDetails(verifier, verifierId).thenComposeAsync((details) -> torusUtils.getPublicAddress(details.getTorusNodeEndpoints(), details.getTorusNodePub(), new VerifierArgs(verifier, verifierId))
                .thenApply((torusPublicKey) -> new Pair(details, torusPublicKey))
        ).thenComposeAsync(pair -> {
            NodeDetails details = (NodeDetails) pair.getKey();
            return torusUtils.retrieveShares(details.getTorusNodeEndpoints(), details.getTorusIndexes(), verifier, verifierParams, idToken).thenApply((shareResponse) -> new Pair(pair.getValue(), shareResponse));
        }).thenComposeAsync(pair -> {
            RetrieveSharesResponse shareResponse = (RetrieveSharesResponse) pair.getValue();
            TorusPublicKey torusPublicKey = (TorusPublicKey) pair.getKey();
            CompletableFuture<TorusKey> response = new CompletableFuture<>();
            if (shareResponse == null) {
                response.completeExceptionally(new Exception("Invalid Share response"));
            } else if (!shareResponse.getEthAddress().equalsIgnoreCase(torusPublicKey.getAddress())) {
                response.completeExceptionally(new Exception("Share response doesn't match public key response"));
            } else {
                response.complete(new TorusKey(shareResponse.getPrivKey(), shareResponse.getEthAddress()));
            }
            return response;
        });
    }
}
