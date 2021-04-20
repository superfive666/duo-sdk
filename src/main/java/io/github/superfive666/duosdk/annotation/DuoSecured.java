package io.github.superfive666.duosdk.annotation;

import java.lang.annotation.*;

/**
 * Annotation allows easy to use DUO authentication API.
 * To turn on this feature, please also include {@link EnableAnnotatedDuoSecurity} in the springboot configuration
 *
 * @see io.github.superfive666.duosdk.params.request.Auth
 * @author superfive
 * @date 2021-04-21
 */

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DuoSecured {

}
