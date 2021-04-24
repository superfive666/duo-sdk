package io.github.superfive666.duosdk.params.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthStatus {
    /**
     * The transaction ID of the authentication attempt, as returned by the /auth endpoint.
     */
    private String txid;
}
