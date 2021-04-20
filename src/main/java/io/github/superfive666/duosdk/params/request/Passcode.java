package io.github.superfive666.duosdk.params.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Authenticate the user with a passcode (from Duo Mobile, SMS, hardware token, or bypass code).
 * @author superfive
 */
@Getter
@Setter
public class Passcode implements DuoAuthApiParam {
    /**
     * Passcode entered by the user.
     */
    private String passcode;
}
