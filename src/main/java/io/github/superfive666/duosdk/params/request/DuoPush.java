package io.github.superfive666.duosdk.params.request;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

/**
 * Authenticate the user with Duo Push.
 *
 * @author superfive
 * @date 2021-04-20
 */
@Getter
@Setter
public class DuoPush implements DuoAuthApiParam {
    /**
     * ID of the device. This device must have the "push" capability.
     *
     * You may also specify "auto" to use the first of the user's devices with the "push" capability.
     */
    private String device;
    /**
     * 	This string is displayed in the Duo Mobile app push notification and UI. The default is "Login Request",
     * 	so the phrase "Login Request" appears in the push notification text and on the request details screen.
     * 	You may wish to specify some alternate phrase for this parameter.
     */
    private String type;
    /**
     * String to display in Duo Mobile in place of the user's Duo username.
     */
    private String displayUsername;
    /**
     * The key-value pairs for the push information details.
     * This will be converted to url-encoded string during API call:
     *
     * A set of URL-encoded key/value pairs with additional contextual information associated with this authentication
     * attempt. The Duo Mobile app will display this information to the user.
     *
     * For example: from=login%20portal&domain=example.com
     *
     * The URL-encoded string's total length must be less than 20,000 bytes.
     */
    private LinkedHashMap<String, String> pushinfo;
}
