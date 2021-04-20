package io.github.superfive666.duosdk.params.request;

/**
 * Authenticate the user with phone callback.
 * @author superfive
 * @date 2021-04-20
 */
public class PhoneCallback implements DuoAuthApiParam {
    /**
     * ID of the device to call. This device must have the "phone" capability.
     *
     * You may also specify "auto" to use the first of the user's devices with the "phone" capability.
     */
    private String device;
}
