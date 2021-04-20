package io.github.superfive666.duosdk.params.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Authenticate the user with phone callback.
 * @author superfive
 */
@Getter
@Setter
public class PhoneCallback implements DuoAuthApiParam {
    /**
     * ID of the device to call. This device must have the "phone" capability.
     *
     * You may also specify "auto" to use the first of the user's devices with the "phone" capability.
     */
    private String device;
}
