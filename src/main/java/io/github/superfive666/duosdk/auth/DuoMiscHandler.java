package io.github.superfive666.duosdk.auth;


import org.springframework.web.client.RestTemplate;

class DuoMiscHandler extends AbstractHandler {
    DuoMiscHandler(RestTemplate duoRestTemplate, String host, String ikey, String skey) {
        super(duoRestTemplate, host, ikey, skey);
    }

}
