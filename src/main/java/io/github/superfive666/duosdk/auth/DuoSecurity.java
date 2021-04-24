package io.github.superfive666.duosdk.auth;

import io.github.superfive666.duosdk.config.DuoSecretConfiguration;
import io.github.superfive666.duosdk.error.DuoInvalidArgumentException;
import io.github.superfive666.duosdk.error.DuoNetworkException;
import io.github.superfive666.duosdk.error.DuoRejectedException;
import io.github.superfive666.duosdk.error.DuoTimeoutException;
import io.github.superfive666.duosdk.params.request.Auth;
import io.github.superfive666.duosdk.params.request.AuthStatus;
import io.github.superfive666.duosdk.params.request.PreAuth;
import io.github.superfive666.duosdk.params.response.AuthResponse;
import io.github.superfive666.duosdk.params.response.PreAuthResponse;
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
    @SuppressWarnings("unused")
    public long ping() {
        return duoMiscHandler.ping().getResponse().getTime() * 1000;
    }

    /**
     * The /preauth endpoint determines whether a user is authorized to log in, and (if so) returns the user's
     * available authentication factors.
     *
     * @param preAuth payload for pre authentication request
     * @return pre-authentication check result
     * @throws DuoInvalidArgumentException If username or user ID parameters did not conform to standard
     * @throws DuoNetworkException If the remote failed to respond
     * @throws DuoRejectedException If the user is not allowed to further authentication process
     */
    public PreAuthResponse preAuth(PreAuth preAuth) throws DuoInvalidArgumentException,
            DuoNetworkException, DuoRejectedException {
        return duoAuthHandler.preAuth(preAuth).getResponse();
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
     * @throws DuoNetworkException If the remote failed to respond
     */
    public AuthResponse auth(Auth auth) throws DuoTimeoutException, DuoRejectedException,
            DuoInvalidArgumentException, DuoNetworkException {
        return duoAuthHandler.auth(auth).getResponse();
    }

    /**
     * The /auth_status endpoint "long-polls" for the next status update from the authentication process for a given
     * transaction. That is to say, if no status update is available at the time the request is sent,
     * it will wait until there is an update before returning a response.
     *
     * @param authStatus payload object containing the transaction id (txid) to check for authentication status
     * @return the authentication status check response, refer to <a href="https://duo.com/docs/authapi#/auth_status">parameters</a> for more details
     * @throws DuoInvalidArgumentException The transaction ID (txid) should not be empty
     * @throws DuoNetworkException If the remote failed to respond
     */
    @SuppressWarnings("unused")
    public AuthResponse authStatus(AuthStatus authStatus) throws DuoInvalidArgumentException,
            DuoNetworkException {
        return duoAuthHandler.authStatus(authStatus).getResponse();
    }
}
