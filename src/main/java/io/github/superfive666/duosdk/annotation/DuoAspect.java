package io.github.superfive666.duosdk.annotation;

import io.github.superfive666.duosdk.auth.DuoSecurity;
import io.github.superfive666.duosdk.error.DuoInvalidArgumentException;
import io.github.superfive666.duosdk.error.DuoNetworkException;
import io.github.superfive666.duosdk.error.DuoRejectedException;
import io.github.superfive666.duosdk.error.DuoTimeoutException;
import io.github.superfive666.duosdk.params.request.Auth;
import io.github.superfive666.duosdk.params.request.DuoPush;
import io.github.superfive666.duosdk.params.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Defining point cuts where methods are annotated with {@link DuoSecured}
 * Annotated method will only be executed after duo authentication success
 *
 * @author superfive
 */
@Aspect
@Slf4j
@RequiredArgsConstructor
public class DuoAspect {
    private final DuoSecurity duoSecurity;

    @Before(value = "@annotation(duoSecured) && args(auth, ..)")
    @SneakyThrows(value = {DuoTimeoutException.class, DuoRejectedException.class,
            DuoInvalidArgumentException.class, DuoNetworkException.class})
    public void duoAuthentication(Auth auth, DuoSecured duoSecured) {
        auth.setAsync(Boolean.FALSE);
        auth.setFactor(duoSecured.mode());

        if (auth.getAdditionalParam() instanceof DuoPush) {
            ((DuoPush) auth.getAdditionalParam()).setType(duoSecured.type());
        }

        AuthResponse response = duoSecurity.auth(auth);
        if (response == null) {
            throw new DuoRejectedException("DUO authentication failed");
        }
    }
}

