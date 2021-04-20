package io.github.superfive666.duosdk.auth;

import io.github.superfive666.duosdk.error.DuoInvalidArgumentException;
import io.github.superfive666.duosdk.error.DuoNetworkException;
import io.github.superfive666.duosdk.error.DuoRejectedException;
import io.github.superfive666.duosdk.error.DuoTimeoutException;
import io.github.superfive666.duosdk.params.DuoAuthenticationMode;
import io.github.superfive666.duosdk.params.request.*;
import io.github.superfive666.duosdk.params.response.AuthResponse;
import io.github.superfive666.duosdk.params.response.DuoBaseResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class DuoAuthHandler extends AbstractHandler {
    DuoAuthHandler(RestTemplate duoRestTemplate, String host, String ikey, String skey) {
        super(duoRestTemplate, host, ikey, skey);
    }

    DuoBaseResponse<AuthResponse> auth(Auth auth) throws DuoTimeoutException, DuoRejectedException,
            DuoInvalidArgumentException, DuoNetworkException {
        final String path = "/auth/v2/auth";
        Pair<HttpHeaders, String> signature = sign(constructParam(auth), HttpMethod.POST.name(), path);
        DuoBaseResponse<AuthResponse> response = duoRestTemplate.exchange(HTTPS_PROTOCOL + host + path,
                HttpMethod.POST, new HttpEntity<>(signature.getRight(), signature.getLeft()),
                new ParameterizedTypeReference<DuoBaseResponse<AuthResponse>>() {}).getBody();
        if (response == null) {
            throw new DuoNetworkException("Remote failed to respond");
        }
        AuthResponse data = response.getResponse();
        if (DuoResult.DENY.name().toLowerCase().equals(data.getResult())) {
            if (DuoResult.TIMEOUT.name().toLowerCase().equals(data.getStatus())) {
                throw new DuoTimeoutException(data.getStatusMessage());
            } else {
                throw new DuoRejectedException(data.getStatusMessage());
            }
        }
        return response;
    }

    private Map<String, String> constructParam(Auth auth) throws DuoInvalidArgumentException {
        Map<String, String> params = new HashMap<>(10);
        
        if (StringUtils.isEmpty(auth.getUserId()) == StringUtils.isEmpty(auth.getUsername())) {
            throw new DuoInvalidArgumentException("Exactly one user_id or username should be specified");
        }
        if (StringUtils.isNotEmpty(auth.getUserId())) {
            params.put("user_id", auth.getUserId());
        } else {
            params.put("username", auth.getUsername());
        }
        params.put("factor", auth.getFactor().name().toLowerCase());

        if (StringUtils.isNotEmpty(auth.getIpaddr())) {
            params.put("ipaddr", auth.getIpaddr());
        }

        if (StringUtils.isNotEmpty(auth.getHostname())) {
            params.put("hostname", auth.getHostname());
        }

        if (auth.isAsync()) {
            params.put("async", "1");
        }

        checkAdditionalApiInformation(auth.getAdditionalParam(), auth.getFactor());

        switch (auth.getFactor()) {
            case PUSH:
                handlePush((DuoPush) auth.getAdditionalParam(), params);
                break;
            case SMS:
                handleSms((Sms) auth.getAdditionalParam(), params);
                break;
            case PHONE:
                handlePhoneCallback((PhoneCallback) auth.getAdditionalParam(), params);
                break;
            case PASSCODE:
                handlePasscode((Passcode) auth.getAdditionalParam(), params);
                break;
            // AUTO mode
            default:
                if (auth.getAdditionalParam() instanceof DuoPush) {
                    handlePush((DuoPush) auth.getAdditionalParam(), params);
                } else {
                    handlePhoneCallback((PhoneCallback) auth.getAdditionalParam(), params);
                }
                break;
        }

        return params;
    }

    private void handlePush(DuoPush param, Map<String, String> params) throws DuoInvalidArgumentException {
        checkDeviceParameter(param.getDevice());
        params.put("device", param.getDevice());
        if (StringUtils.isNotEmpty(param.getType())) {
            params.put("type", param.getType());
        }
        if (StringUtils.isNotEmpty(param.getDisplayUsername())) {
            params.put("display_username", param.getDisplayUsername());
        }
        if (param.getPushinfo() != null && !param.getPushinfo().isEmpty()) {
            params.put("pushinfo", param.getPushinfo().entrySet()
                    .stream()
                    .map(e -> String.format("%s=%s", e.getKey(), e.getValue()))
                    .collect(Collectors.joining("&")));
        }
    }

    private void handleSms(Sms param, Map<String, String> params) throws DuoInvalidArgumentException {
        checkDeviceParameter(param.getDevice());
        params.put("device", param.getDevice());
    }

    private void handlePhoneCallback(PhoneCallback param, Map<String, String> params) throws DuoInvalidArgumentException {
        checkDeviceParameter(param.getDevice());
        params.put("device", param.getDevice());
    }

    private void handlePasscode(Passcode param, Map<String, String> params) throws DuoInvalidArgumentException {
        notEmpty(param.getPasscode(), "Passcode is a required parameter for passcode authentication");
        params.put("passcode", param.getPasscode());
    }

    private void checkDeviceParameter(String device) throws DuoInvalidArgumentException {
        notEmpty(device, "Device parameter is required");
    }

    private void notEmpty(String str, String message) throws DuoInvalidArgumentException {
        if (StringUtils.isEmpty(str)) {
            throw new DuoInvalidArgumentException(message);
        }
    }

    private void checkAdditionalApiInformation(DuoAuthApiParam param, DuoAuthenticationMode mode)
            throws DuoInvalidArgumentException{
        if (!ArrayUtils.contains(mode.getValid(), param.getClass())) {
            throw new DuoInvalidArgumentException("Wrong parameter for DuoAuthApiParam");
        }
    }
}
