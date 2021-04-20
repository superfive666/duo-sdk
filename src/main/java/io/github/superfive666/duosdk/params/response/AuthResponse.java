package io.github.superfive666.duosdk.params.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DUO authentication API response value object
 * For non-async authentication, "result", "status", "status_message" will be returned. For async authentication,
 * "txid" will be returned
 * @author superfive
 */
@Getter
@Setter
public class AuthResponse {
    @JsonProperty("result")
    private String result;
    @JsonProperty("status")
    private String status;
    @JsonProperty("status_message")
    private String statusMessage;
    @JsonProperty("txid")
    private String txid;
}
