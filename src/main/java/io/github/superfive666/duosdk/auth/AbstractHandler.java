package io.github.superfive666.duosdk.auth;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
abstract class AbstractHandler {
    protected static final String HTTPS_PROTOCOL = "https://";
    protected final RestTemplate duoRestTemplate;
    protected final String host;
    private final String ikey;
    private final String skey;

    protected Pair<HttpHeaders, String> sign(Map<String, String> params, String method, String path) {
        String now = OffsetDateTime.now().format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z"));
        final String args = params.entrySet().stream()
                .filter(e -> StringUtils.isNotEmpty(e.getValue()))
                .sorted(Map.Entry.comparingByKey())
                .map(e -> String.format("%s=%s",
                        URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8).replace("+", "%20"),
                        URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8).replace("+", "%20")))
                .collect(Collectors.joining("&"));
        String canon = StringUtils.join(new String[]{now, method.toUpperCase(), host.toLowerCase(), path, args}, "\n");
        String signature = signature(canon);
        final String authorization = Base64.encodeBase64String(String.format("%s:%s", ikey, signature).getBytes(StandardCharsets.UTF_8));
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Date", now);
        headers.add("Authorization", "Basic " + authorization);
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        return new ImmutablePair<>(headers, args);
    }

    @SneakyThrows(value = {NoSuchAlgorithmException.class, InvalidKeyException.class})
    private String signature(String canon) {
        Mac mac = Mac.getInstance(HmacAlgorithms.HMAC_SHA_1.getName());
        SecretKeySpec macKey = new SecretKeySpec(skey.getBytes(StandardCharsets.UTF_8), "RAW");
        mac.init(macKey);
        return Hex.encodeHexString(mac.doFinal(canon.getBytes(StandardCharsets.UTF_8)));
    }
}
