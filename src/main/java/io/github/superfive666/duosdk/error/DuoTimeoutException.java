package io.github.superfive666.duosdk.error;

/**
 * No timely acknowledge on the duo authentication request
 * @author superfive
 */
public class DuoTimeoutException extends Exception {
    public DuoTimeoutException(String message) { super(message); }
}
