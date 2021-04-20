package io.github.superfive666.duosdk.config;

import io.github.superfive666.duosdk.annotation.DuoAspect;
import io.github.superfive666.duosdk.auth.DuoSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * Configuration for enabling DUO Aspect so as to intercept methods annotated with
 * {@link io.github.superfive666.duosdk.annotation.DuoSecured} annotation
 * @author superfive
 */
@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class DuoAnnotationConfiguration {
    private final DuoSecurity duoSecurity;

    @Bean
    public DuoAspect duoAspect() {
        return new DuoAspect(duoSecurity);
    }
}
