package io.github.superfive666.duosdk.params.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Parameters for /auth_status api
 * <a href="https://duo.com/docs/authapi#/auth_status">DUO #auth_status</a>
 * @author superfive
 */
@Getter
@Setter
public class AuthStatus {
    /**
     * The transaction ID of the authentication attempt, as returned by the /auth endpoint.
     */
    private String txid;
}
