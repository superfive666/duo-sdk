package io.github.superfive666.duosdk.params.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DuoDevice {
    /**
     * Identifies which of the user's devices this is.
     */
    @JsonProperty("device")
    private String device;
    /**
     * "phone" or "token".
     */
    @JsonProperty("type")
    private String type;
    /**
     * Phone number of the device, with all but the last four digits obscured. Or, if the device has no
     * associated number, the empty string ("").
     */
    @JsonProperty("number")
    private String number;
    /**
     * Device's name. Or, if the device has not been named, the empty string ("").
     */
    @JsonProperty("name")
    private String name;
    /**
     * List of strings, each a factor that can be used with the device.
     * Note that hardware tokens do not have any associated capabilities.
     * <ul>
     *     <li>auto:	    The device is valid for automatic factor selection (e.g. phone or push).</li>
     *     <li>push:	    The device is activated for Duo Push.</li>
     *     <li>sms:	        The device can receive batches of SMS passcodes.</li>
     *     <li>phone:	    The device can receive phone calls.</li>
     *     <li>mobile_otp:	The device is capable of generating passcodes with the Duo Mobile app.</li>
     * </ul>
     */
    @JsonProperty("capabilities")
    private List<String> capabilities;
}
