package io.github.superfive666.duosdk.auth;

import org.springframework.web.client.RestTemplate;

class DuoEnrollHandler extends AbstractHandler {
    DuoEnrollHandler(RestTemplate duoRestTemplate) { super(duoRestTemplate); }

}
