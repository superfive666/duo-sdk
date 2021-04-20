package io.github.superfive666.duosdk.annotation;

import io.github.superfive666.duosdk.config.DuoAnnotationConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * This is the switch annotation for springboot configuration.
 * To use {@link DuoSecured} annotation on public method, this configuration annotation needs to be put
 * under any springboot configuration class.
 * Turning on this setting turns on the {@link EnableDuoSecurity} configuration by default
 *
 * @author superfive
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DuoAnnotationConfiguration.class)
@EnableDuoSecurity
public @interface EnableAnnotatedDuoSecurity {
}
