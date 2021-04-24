package io.github.superfive666.duosdk.params.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreAuthResponse {
    @JsonProperty("result")
    private String result;
    @JsonProperty("status")
    private String status;
    @JsonProperty("status_msg")
    private String statusMessage;
    /**
     * If result is "enroll" a unique, enrollment portal URL is returned. This URL may be passed to the user and
     * opened in a new browser window to access a portal that will help the user associate a device with the
     * user_id specified or returned when calling /preauth. The enrollment URL is valid for five minutes after generation.
     *
     * This enrollment portal URL should be used instead of calling /enroll if your users need to enroll phones
     * that are not capable of running Duo Mobile, such as landlines or cell phones. /enroll only supports enrolling
     * smartphones that can run Duo Mobile.
     *
     * This field will only be present if result is "enroll".
     */
    @JsonProperty("enroll_portal_url")
    private String enrollPortalUrl;
    /**
     * A list of the user's devices, where each device is a series of key/value pairs.
     *
     * This field will only be present if result is "auth".
     */
    @JsonProperty("devices")
    private List<DuoDevice> devices;
}
