package io.github.superfive666.duosdk.params.request;

/**
 * Send a new batch of SMS passcodes to the user. Note that this will not actually authenticate the user
 * (it will automatically return "deny"). Thus, if the user elects to do this then you should re-prompt to
 * authenticate after the call has completed.
 * @author superfive
 */
public class Sms implements DuoAuthApiParam {
    /**
     * ID of the device to send passcodes to. This device must have the "sms" capability.
     *
     * You may also specify "auto" to use the first of the user's devices with the "sms" capability.
     */
    private String device;
}
