package io.github.superfive666.duosdk.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Internally used rest template proxy customizer
 * @author superfive
 * @date 2021-04-20
 */
@RequiredArgsConstructor
final class ProxyCustomizer implements RestTemplateCustomizer {
    private final String proxyUrl;
    private final int proxyPort;
    private final String proxyUsername;
    private final String proxyPassword;

    @Override
    @SuppressWarnings("all")
    @SneakyThrows(value = {NoSuchAlgorithmException.class, KeyManagementException.class})
    public void customize(RestTemplate restTemplate) {
        X509TrustManager x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            @Override
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        };
        final HttpHost proxy = new HttpHost(proxyUrl, proxyPort);
        final AuthScope scope = new AuthScope(proxyUrl, proxyPort);
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(scope, new UsernamePasswordCredentials(proxyUsername, proxyPassword));
        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
        final SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
        final HttpClient httpClient = HttpClientBuilder.create()
                .setProxy(proxy)
                .setDefaultCredentialsProvider(credentialsProvider)
                .disableCookieManagement()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}
