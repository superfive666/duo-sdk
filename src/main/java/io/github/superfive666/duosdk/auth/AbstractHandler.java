package io.github.superfive666.duosdk.auth;

import io.github.superfive666.duosdk.params.response.DuoBaseResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
abstract class AbstractHandler {
    private final RestTemplate duoRestTemplate;
    private final String host;
    private final String ikey;
    private final String skey;

    protected HttpHeaders sign(Map<String, String> params, String method, String path) {
        String now = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z").format(LocalDateTime.now());
        String args = params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> String.format("%s=%s", URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8), URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8)))
                .collect(Collectors.joining("&"));
        String canon = StringUtils.join(new String[]{now, method.toUpperCase(), host.toLowerCase(), path, args}, "\n");
        String signature = signature(canon);
        String authorization = Base64.encodeBase64String(String.format("%s:%s", ikey, signature).getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Date", now);
        headers.add("Authorization", "Basic " + authorization);
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }

    private String signature(String canon) {
        
        return null;
    }

    protected <T> DuoBaseResponse<T> get(String url, HttpHeaders headers) {
        return duoRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<DuoBaseResponse<T>>() {}).getBody();
    }

    protected <T> DuoBaseResponse<T> post(String url, HttpHeaders headers, String data) {
        return duoRestTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(data, headers),
                new ParameterizedTypeReference<DuoBaseResponse<T>>() {}).getBody();
    }
}
