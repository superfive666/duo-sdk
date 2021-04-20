package io.github.superfive666.duosdk.config;

import io.github.superfive666.duosdk.error.DuoIllegalConfigurationException;
import lombok.SneakyThrows;

class DuoCheck {
    @SneakyThrows(DuoIllegalConfigurationException.class)
    public static void isTrue(boolean literal, String message) {
        if (!literal) {
            throw new DuoIllegalConfigurationException(message);
        }
    }
}
