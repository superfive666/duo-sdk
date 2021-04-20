package io.github.superfive666.duosdk.auth;

enum DuoResult {
    /**
     * Allow access - authentication success
     */
    ALLOW,
    /**
     * Deny access - either mistake or fraud reported
     */
    DENY,
    /**
     * Timeout due to un-attended authentication request
     */
    TIMEOUT
}
