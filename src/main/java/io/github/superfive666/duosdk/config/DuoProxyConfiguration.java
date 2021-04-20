package io.github.superfive666.duosdk.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * The configuration allows for use of proxy for any DUO authentication request.
 * Default configuration for proxy-enabled is false. If proxy-enabled is set to true, the following properties is
 * required to be configured:
 * <ul>
 *     <li>duo.proxy.proxy-url: The full qualified url for the proxy server</li>
 *     <li>duo.proxy.proxy-port: The proxy listening port number</li>
 *     <li>duo.proxy.proxy-username: The username authentication to proxy server (can be empty if no authentication required)</li>
 *     <li>duo.proxy.proxy-password: The password of authentication (can be empty if no authentication required)</li>
 * </ul>
 * Currently this version supports username password (if any) authentication method for proxy server
 * <b>Turn on debug mode to see intercepted request and response for DUO api call</b>
 * @author superfive
 */
@Getter
@Setter
@Slf4j
@Configuration
@ConfigurationProperties("duo.proxy")
public class DuoProxyConfiguration {
    private boolean proxyEnabled = false;
    private String proxyUrl = StringUtils.EMPTY;
    private int proxyPort = 0;
    private String proxyUsername = StringUtils.EMPTY;
    private String proxyPassword = StringUtils.EMPTY;

    @PostConstruct
    public void checkConfigurationValid() {
        log.debug("DUO Proxy Configuration Enabled: {}", proxyEnabled);
        if (proxyEnabled) {
            DuoCheck.isTrue(StringUtils.isNotEmpty(proxyUrl), "Proxy is enabled but proxy URL is not configured");
            DuoCheck.isTrue(proxyPort > 0, "Proxy is enabled but proxy port is not valid");
            DuoCheck.isTrue(StringUtils.isNotEmpty(proxyUsername), "Proxy is enabled but proxy username is not configured");
            DuoCheck.isTrue(StringUtils.isNotEmpty(proxyPassword), "Proxy is enabled but proxy password is not configured");

            log.debug("Proxy URL configured: {}", proxyUrl);
            log.debug("Proxy port configured: {}", proxyPort);
            log.debug("Proxy username configured: {}", proxyUsername);
            // Proxy password can be of variable length
            log.debug("Proxy password configured: masked[{}]", "*".repeat(10));
            log.info("DUO Security proxy configuration is properly set");
        }
    }

    @Bean
    public RestTemplate duoRestTemplate() {
        RestTemplate duoRestTemplate;
        if (proxyEnabled) {
            // setup proxy factory if proxy enabled
            ProxyCustomizer customizer = new ProxyCustomizer(proxyUrl, proxyPort, proxyUsername, proxyPassword);
            duoRestTemplate = new RestTemplateBuilder(customizer).build();
        } else {
            duoRestTemplate = new RestTemplate();
        }
        // set debugger interceptors
        duoRestTemplate.getInterceptors().add((request, body, execution) -> {
            // Request parameters log
            log.debug("Request URL: {}", request.getURI().toString());
            log.debug("Http headers: {}", request.getHeaders().toString());
            if (body.length > 0) {
                log.debug("Request body: {}", new String(body, StandardCharsets.UTF_8));
            }
            ClientHttpResponse clientHttpResponse = execution.execute(request, body);
            // Response details log
            log.debug("Http response status: {}", clientHttpResponse.getRawStatusCode());
            log.debug("Http response headers: {}", clientHttpResponse.getHeaders().toString());
            byte[] buffer;
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                IOUtils.copy(clientHttpResponse.getBody(), baos);
                buffer = baos.toByteArray();
            }
            log.debug("Http response body: {}", new String(buffer, StandardCharsets.UTF_8));
            return new ClientHttpResponse() {
                @Override
                public HttpStatus getStatusCode() throws IOException { return clientHttpResponse.getStatusCode(); }
                @Override
                public int getRawStatusCode() throws IOException { return clientHttpResponse.getRawStatusCode(); }
                @Override
                public String getStatusText() throws IOException { return clientHttpResponse.getStatusText(); }
                @Override
                public void close() { }
                @Override
                public InputStream getBody() throws IOException { return new ByteArrayInputStream(buffer); }
                @Override
                public HttpHeaders getHeaders() { return clientHttpResponse.getHeaders(); }
            };
        });
        return duoRestTemplate;
    }
}
