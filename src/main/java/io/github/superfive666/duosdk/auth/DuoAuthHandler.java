package io.github.superfive666.duosdk.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
class DuoAuthHandler {
    private final RestTemplate duoRestTemplate;
}
