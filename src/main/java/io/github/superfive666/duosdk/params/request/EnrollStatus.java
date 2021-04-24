package io.github.superfive666.duosdk.params.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Parameters for /enroll_status api
 * <a href="https://duo.com/docs/authapi#/enroll_status">DUO #enroll_status</a>
 * @author superfive
 */
@Getter
@Setter
public class EnrollStatus {
    /**
     * ID of the user.
     */
    private String userId;
    /**
     * Activation code, as returned from /enroll.
     */
    private String activationCode;
}
