package io.github.superfive666.duosdk.error;

/**
 * Request parameter is invalid
 * @author superfive
 */
public class DuoInvalidArgumentException extends Exception {
    public DuoInvalidArgumentException(String message) {
        super(message);
    }
}
