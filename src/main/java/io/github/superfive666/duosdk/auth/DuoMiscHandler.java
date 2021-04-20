package io.github.superfive666.duosdk.auth;


import io.github.superfive666.duosdk.params.response.DuoBaseResponse;
import io.github.superfive666.duosdk.params.response.PingResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

class DuoMiscHandler extends AbstractHandler {
    DuoMiscHandler(RestTemplate duoRestTemplate, String host, String ikey, String skey) {
        super(duoRestTemplate, host, ikey, skey);
    }

    public DuoBaseResponse<PingResponse> ping() {
        final String path = "/auth/v2/ping";
        return duoRestTemplate.exchange(HTTPS_PROTOCOL + host + path, HttpMethod.GET,
                new HttpEntity<>(null, null),
                new ParameterizedTypeReference<DuoBaseResponse<PingResponse>>() {}).getBody();
    }
}
