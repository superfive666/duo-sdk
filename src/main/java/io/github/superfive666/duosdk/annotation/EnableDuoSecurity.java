package io.github.superfive666.duosdk.annotation;

import io.github.superfive666.duosdk.config.DuoProxyConfiguration;
import io.github.superfive666.duosdk.config.DuoSecretConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Include this annotation if wish to use {@link io.github.superfive666.duosdk.auth.DuoSecurity} as part of the
 * application context bean
 * @author superfive
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {DuoSecretConfiguration.class, DuoProxyConfiguration.class})
public @interface EnableDuoSecurity {
}
