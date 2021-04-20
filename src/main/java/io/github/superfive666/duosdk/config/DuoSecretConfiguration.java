package io.github.superfive666.duosdk.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Configuration class that allows injection of properties via application.properties/application.yml
 * <p>Example configuration for application.properties:
 * <pre>
 *     duo.secret.host=api-XXXXXXXX.duosecurity.com
 *     duo.secret.ikey=DIWJ8X6AEYOR5OMC6TQ1
 *     duo.secret.skey=Zh5eGmUq9zpfQnyUIu5OL9iWoMMv5ZNmk3zLJ4Ep
 * </pre>
 * </p>
 * <p>Example configuration for application.yml:
 * <pre>
 *     duo.secret:
 *       host: api-XXXXXXXX.duosecurity.com
 *       ikey: DIWJ8X6AEYOR5OMC6TQ1
 *       skey: Zh5eGmUq9zpfQnyUIu5OL9iWoMMv5ZNmk3zLJ4Ep
 * </pre>
 * </p>
 * <p>For more information on where to check the above credential for your DUO account, please refer to
 * <a href="https://duo.com/docs/authapi#api-details">
 * DUO Auth API</a>
 * </p>
 *
 * @author superfive
 * @date 2021-04-20
 */
@Getter
@Setter
@Slf4j
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("duo.secret")
public class DuoSecretConfiguration {
    private String host;
    private String ikey;
    private String skey;

    @PostConstruct
    public void debug() {
        log.debug("Host value injected: {}", host);
        log.debug("Integration key injected: {}", ikey);
        // DUO Secret key is of fixed length
        log.debug("Secret key injected: masked[{}]", "*".repeat(skey.length()));

        log.info("DUO Security plugin is properly autowired into the application context");
    }
}
