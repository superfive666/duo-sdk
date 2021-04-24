package io.github.superfive666.duosdk.params.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollResponse {
    /**
     * URL for an image of a QR code with the activation code.
     */
    @JsonProperty("activation_barcode")
    private String activationBarcode;
    /**
     * Code to enter into the Duo Mobile app to add the account. On phones with Duo Mobile already installed it
     * will be a clickable link.
     */
    @JsonProperty("activation_code")
    private String activationCode;
    /**
     * Time at which this activation code will expire. Formatted as a UNIX timestamp. Integer.
     */
    @JsonProperty("expiration")
    private int expiration;
    /**
     * Permanent, unique identifier for the user in Duo. Always generated.
     */
    @JsonProperty("user_id")
    private String userId;
    /**
     * Unique name for the user in Duo. Either specified as a parameter or auto-generated.
     */
    @JsonProperty("username")
    private String username;
}
