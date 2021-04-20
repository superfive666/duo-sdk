package io.github.superfive666.duosdk.auth;

import io.github.superfive666.duosdk.error.DuoRejectedException;
import io.github.superfive666.duosdk.error.DuoTimeoutException;
import io.github.superfive666.duosdk.params.request.Auth;
import io.github.superfive666.duosdk.params.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 *
 * @author superfive
 * @date 2021-04-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DuoSecurity {
    private final RestTemplate duoRestTemplate;
    private DuoAuthHandler duoAuthHandler;
    private DuoEnrollHandler duoEnrollHandler;
    private DuoMiscHandler duoMiscHandler;

    @PostConstruct
    public void initHandlers() {
        duoAuthHandler      = new DuoAuthHandler(duoRestTemplate);
        duoEnrollHandler    = new DuoEnrollHandler(duoRestTemplate);
        duoMiscHandler      = new DuoMiscHandler(duoRestTemplate);

        Assert.notNull(duoAuthHandler, "Configuration error");
        Assert.notNull(duoEnrollHandler, "Configuration error");
        Assert.notNull(duoMiscHandler, "Configuration error");
    }


    /**
     * Initiate the DUO api authentication (push, sms, passcode, phone modes available)
     *
     * @param auth The DUO authentication payload dictating the behaviour of the authentication
     * @see Auth
     * @return  The final outcome of the DUO api call
     * @throws DuoTimeoutException If the DUO authentication is not promptly attended to, it causes timeout on authentication API
     * @throws DuoRejectedException If the DUO authentication is "deny" (reason can be fraud or mistake)
     */
    public AuthResponse auth(Auth auth) throws DuoTimeoutException, DuoRejectedException {
        return duoAuthHandler.auth(auth).getResponse();
    }

    /**
     * TODO: to be removed
     */
    public void dummy() {
        Assert.notNull(duoAuthHandler, "Configuration error");
        Assert.notNull(duoEnrollHandler, "Configuration error");
        Assert.notNull(duoMiscHandler, "Configuration error");
    }
}
