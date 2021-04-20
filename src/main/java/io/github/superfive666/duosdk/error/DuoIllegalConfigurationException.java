package io.github.superfive666.duosdk.error;

/**
 * The application startup configuration is not properly set.
 * This will cause the DUO 2FA sdk to not work as intended.
 *
 * @author superfive
 */
public class DuoIllegalConfigurationException extends Exception {
    public DuoIllegalConfigurationException(String message) {
        super(message);
    }
}
