package io.github.superfive666.duosdk.params.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Base API response from DUO security.
 * <pre>
 *     {
 *         "stat": "ok",
 *         "response": {...}
 *     }
 * </pre>
 * @param <T> Response value object depending on the json returned
 * @author superfive
 * @date 2021-04-20
 */
@Getter
@Setter
public class DuoBaseResponse<T> {
    @JsonProperty("stat")
    private String stat;
    @JsonProperty("response")
    private T response;
}
