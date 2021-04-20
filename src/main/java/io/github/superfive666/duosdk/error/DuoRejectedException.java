package io.github.superfive666.duosdk.error;

/**
 * Authentication failed
 * @author superfive
 */
public class DuoRejectedException extends Exception {
    public DuoRejectedException(String message) {
        super(message);
    }
}
