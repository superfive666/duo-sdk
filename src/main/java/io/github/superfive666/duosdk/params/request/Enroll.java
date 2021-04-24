package io.github.superfive666.duosdk.params.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Parameters for /enroll api
 * <a href="https://duo.com/docs/authapi#/enroll">DUO #enroll</a>
 * @author superfive
 */
@Getter
@Setter
public class Enroll {
    /**
     * Username for the created user. If not given, a random username will be assigned and returned.
     */
    private String username;
    /**
     * Seconds for which the activation code will remain valid. Default: 86400 (one day).
     */
    private int validSecs = 86400;
}
