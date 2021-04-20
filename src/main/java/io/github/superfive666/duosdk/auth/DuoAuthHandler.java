package io.github.superfive666.duosdk.auth;

import io.github.superfive666.duosdk.error.DuoRejectedException;
import io.github.superfive666.duosdk.error.DuoTimeoutException;
import io.github.superfive666.duosdk.params.request.Auth;
import io.github.superfive666.duosdk.params.response.AuthResponse;
import io.github.superfive666.duosdk.params.response.DuoBaseResponse;
import org.springframework.web.client.RestTemplate;

class DuoAuthHandler extends AbstractHandler {
    DuoAuthHandler(RestTemplate duoRestTemplate, String host, String ikey, String skey) {
        super(duoRestTemplate, host, ikey, skey);
    }

    DuoBaseResponse<AuthResponse> auth(Auth auth) throws DuoTimeoutException, DuoRejectedException {

        return null;
    }
}
