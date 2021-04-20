package io.github.superfive666.duosdk.annotation;

import java.lang.annotation.*;

/**
 * This is the switch annotation for springboot configuration.
 * To use {@link DuoSecured} annotation on public method, this configuration annotation needs to be put
 * under any springboot configuration class.
 *
 * @author superfive
 * @date 2021-04-20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableAnnotatedDuoSecurity {
}
