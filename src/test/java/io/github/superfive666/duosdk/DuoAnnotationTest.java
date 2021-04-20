package io.github.superfive666.duosdk;

import io.github.superfive666.duosdk.annotation.DuoSecured;
import io.github.superfive666.duosdk.annotation.EnableAnnotatedDuoSecurity;
import io.github.superfive666.duosdk.params.DuoAuthenticationMode;
import io.github.superfive666.duosdk.params.request.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableAnnotatedDuoSecurity
public class DuoAnnotationTest {

    @DuoSecured(type = "title1", mode = DuoAuthenticationMode.AUTO)
    public void triggerAuthAutoTitle1(Auth auth) {
        log.info("Do something after authentication success");
    }

    @DuoSecured
    public void triggerAuthNoValue(Auth auth) {
        log.info("Do something after authentication success");
    }

    @DuoSecured
    public void triggerAuthFail(Auth auth) {
        log.info("This log should not be seen when duo auth did not pass");
    }
}
