package io.github.superfive666.duosdk.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
class DuoMiscHandler {
    private final RestTemplate duoRestTemplate;
}
