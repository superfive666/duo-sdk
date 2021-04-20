package io.github.superfive666.duosdk.auth;

import io.github.superfive666.duosdk.error.DuoInvalidArgumentException;
import io.github.superfive666.duosdk.error.DuoRejectedException;
import io.github.superfive666.duosdk.error.DuoTimeoutException;
import io.github.superfive666.duosdk.params.request.Auth;
import io.github.superfive666.duosdk.params.response.AuthResponse;
import io.github.superfive666.duosdk.params.response.DuoBaseResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

class DuoAuthHandler extends AbstractHandler {
    DuoAuthHandler(RestTemplate duoRestTemplate, String host, String ikey, String skey) {
        super(duoRestTemplate, host, ikey, skey);
    }

    DuoBaseResponse<AuthResponse> auth(Auth auth) throws DuoTimeoutException, DuoRejectedException, DuoInvalidArgumentException {
        final String path = "/auth/v2/auth";
        Pair<HttpHeaders, String> signature = sign(constructParam(auth), HttpMethod.POST.name(), path);
        DuoBaseResponse<AuthResponse> response = post(HTTPS_PROTOCOL + host + path, signature.getLeft(), signature.getRight());
        AuthResponse data = response.getResponse();

        return response;
    }

    private Map<String, String> constructParam(Auth auth) throws DuoInvalidArgumentException {
        Map<String, String> params = new HashMap<>(10);



        return params;
    }
}
