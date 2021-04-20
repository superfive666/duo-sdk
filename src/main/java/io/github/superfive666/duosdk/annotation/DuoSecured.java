package io.github.superfive666.duosdk.annotation;

import io.github.superfive666.duosdk.params.DuoAuthenticationMode;

import java.lang.annotation.*;

/**
 * Annotation allows easy to use DUO authentication API.
 * To turn on this feature, please also include {@link EnableAnnotatedDuoSecurity} in the springboot configuration
 * Specify the mode of duo authentication (i.e. push, sms, phone, passcode), default is
 * {@link DuoAuthenticationMode #PUSH}. Annotate the title of the push notification screen (if mode is push).
 * Any method annotated with this, the authentication type has to be synchronous.
 * <b>Important: only method annotated and with an argument</b>
 *
 * @see io.github.superfive666.duosdk.params.request.Auth
 * @author superfive
 */

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DuoSecured {
    String type() default "";

    DuoAuthenticationMode mode() default DuoAuthenticationMode.PUSH;
}