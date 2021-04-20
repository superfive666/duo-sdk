package io.github.superfive666.duosdk.auth;

import io.github.superfive666.duosdk.config.DuoSecretConfiguration;
import io.github.superfive666.duosdk.error.DuoInvalidArgumentException;
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
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DuoSecurity {
    private final RestTemplate duoRestTemplate;
    private final DuoSecretConfiguration duoSecretConfiguration;
    private DuoAuthHandler duoAuthHandler;
    private DuoEnrollHandler duoEnrollHandler;
    private DuoMiscHandler duoMiscHandler;

    @PostConstruct
    public void initHandlers() {
        final String host = duoSecretConfiguration.getHost();
        final String ikey = duoSecretConfiguration.getIkey();
        final String skey = duoSecretConfiguration.getSkey();

        duoAuthHandler      = new DuoAuthHandler(duoRestTemplate, host, ikey, skey);
        duoEnrollHandler    = new DuoEnrollHandler(duoRestTemplate, host, ikey, skey);
        duoMiscHandler      = new DuoMiscHandler(duoRestTemplate, host, ikey, skey);

        Assert.notNull(duoAuthHandler, "Configuration error");
        Assert.notNull(duoEnrollHandler, "Configuration error");
        Assert.notNull(duoMiscHandler, "Configuration error");
    }

    /**
     * Ping the liveliness of the DUO api
     *
     * @return The ping check time in milliseconds of epoch time
     */
    public long ping() {
        return duoMiscHandler.ping().getResponse().getTime() * 1000;
    }

    /**
     * Initiate the DUO api authentication (push, sms, passcode, phone modes available)
     *
     * @param auth The DUO authentication payload dictating the behaviour of the authentication
     * @see Auth
     * @return  The final outcome of the DUO api call
     * @throws DuoTimeoutException If the DUO authentication is not promptly attended to, it causes timeout on authentication API
     * @throws DuoRejectedException If the DUO authentication is "deny" (reason can be fraud or mistake)
     * @throws DuoInvalidArgumentException If the parameter passed in is not valid
     */
    public AuthResponse auth(Auth auth) throws DuoTimeoutException, DuoRejectedException, DuoInvalidArgumentException {
        return duoAuthHandler.auth(auth).getResponse();
    }
}
