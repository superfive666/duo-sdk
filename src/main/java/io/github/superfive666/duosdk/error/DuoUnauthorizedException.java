package io.github.superfive666.duosdk.error;

/**
 * HTTP status code 401
 * @author superfive
 */
public class DuoUnauthorizedException extends RuntimeException {
    public DuoUnauthorizedException(String message) { super(message); }
}
