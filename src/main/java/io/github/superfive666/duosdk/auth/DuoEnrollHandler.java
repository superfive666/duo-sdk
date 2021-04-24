package io.github.superfive666.duosdk.auth;

import io.github.superfive666.duosdk.error.DuoInvalidArgumentException;
import io.github.superfive666.duosdk.error.DuoNetworkException;
import io.github.superfive666.duosdk.params.request.Enroll;
import io.github.superfive666.duosdk.params.request.EnrollStatus;
import io.github.superfive666.duosdk.params.response.DuoBaseResponse;
import io.github.superfive666.duosdk.params.response.EnrollResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

class DuoEnrollHandler extends AbstractHandler {
    DuoEnrollHandler(RestTemplate duoRestTemplate, String host, String ikey, String skey) {
        super(duoRestTemplate, host, ikey, skey);
    }

    DuoBaseResponse<EnrollResponse> enroll(Enroll enroll) throws DuoNetworkException {
        final String path = "/auth/v2/enroll";
        Pair<HttpHeaders, String> signature = sign(constructParams(enroll), HttpMethod.POST.name(), path);
        DuoBaseResponse<EnrollResponse> response = duoRestTemplate.exchange(HTTPS_PROTOCOL + host + path,
                HttpMethod.POST, new HttpEntity<>(signature.getRight(), signature.getLeft()),
                new ParameterizedTypeReference<DuoBaseResponse<EnrollResponse>>() {}).getBody();
        if (response == null) {
            throw new DuoNetworkException("DUO remote failed to respond");
        }
        return response;
    }

    DuoBaseResponse<String> enrollStatus(EnrollStatus enrollStatus) throws DuoInvalidArgumentException, DuoNetworkException {
        final String path = "/auth/v2/enroll_status";
        Pair<HttpHeaders, String> signature = sign(constructParams(enrollStatus), HttpMethod.POST.name(), path);
        DuoBaseResponse<String> response = duoRestTemplate.exchange(HTTPS_PROTOCOL + host + path,
                HttpMethod.POST, new HttpEntity<>(signature.getRight(), signature.getLeft()),
                new ParameterizedTypeReference<DuoBaseResponse<String>>() {}).getBody();
        if (response == null) {
            throw new DuoNetworkException("DUO remote failed to respond");
        }
        return response;
    }

    private Map<String, String> constructParams(EnrollStatus enrollStatus) throws DuoInvalidArgumentException {
        Map<String, String> params = new HashMap<>(2);
        if (StringUtils.isEmpty(enrollStatus.getUserId())) {
            throw new DuoInvalidArgumentException("User ID is required parameter to check enroll status");
        }
        if (StringUtils.isEmpty(enrollStatus.getActivationCode())) {
            throw new DuoInvalidArgumentException("Activation code is required parameter to check enroll status");
        }

        params.put("user_id", enrollStatus.getUserId());
        params.put("activation_code", enrollStatus.getActivationCode());
        return params;
    }

    private Map<String, String> constructParams(Enroll enroll) {
        Map<String, String> params = new HashMap<>(2);
        if (StringUtils.isNotEmpty(enroll.getUsername())) {
            params.put("username", enroll.getUsername());
        }

        params.put("valid_secs", String.valueOf(enroll.getValidSecs()));
        return params;
    }
}
