package io.github.superfive666.duosdk.params.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The /ping endpoint acts as a "liveliness check" that can be called to verify that Duo is up before trying to call
 * other Auth API endpoints. Unlike the other endpoints, this one does not have to be signed with the Authorization header.
 *
 * This endpoint is also suitable for use with Duo's v2 Web SDK to verify that Duo's service is responding before
 * initializing frame authentication.
 * @author superfive
 */
@Getter
@Setter
public class PingResponse {
    @JsonProperty("time")
    private long time;
}
