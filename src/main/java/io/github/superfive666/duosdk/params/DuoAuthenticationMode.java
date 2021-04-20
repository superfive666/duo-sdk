package io.github.superfive666.duosdk.params;

/**
 * Factor to use for authentication.
 * See more on <a href="https://duo.com/docs/authapi#/auth">DUO #auth</a>
 *
 * @author superfive
 */
public enum DuoAuthenticationMode {
    /**
     * Use the out-of-band factor (push or phone) recommended by Duo as the best for the user's devices.
     */
    AUTO,
    /**
     * Authenticate the user with Duo Push.
     */
    PUSH,
    /**
     * Authenticate the user with a passcode (from Duo Mobile, SMS, hardware token, or bypass code).
     */
    PASSCODE,
    /**
     * Send a new batch of SMS passcodes to the user. Note that this will not actually authenticate the user
     * (it will automatically return "deny"). Thus, if the user elects to do this then you should re-prompt to
     * authenticate after the call has completed.
     */
    SMS,
    /**
     * Authenticate the user with phone callback.
     */
    PHONE
}
