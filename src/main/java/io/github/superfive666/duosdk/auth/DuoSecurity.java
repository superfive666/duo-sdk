package io.github.superfive666.duosdk.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 *
 * @author superfive
 * @date 2021-04-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DuoSecurity {
    private final RestTemplate duoRestTemplate;
    private DuoAuthHandler duoAuthHandler;
    private DuoEnrollHandler duoEnrollHandler;
    private DuoMiscHandler duoMiscHandler;

    @PostConstruct
    public void initHandlers() {
        duoAuthHandler      = new DuoAuthHandler(duoRestTemplate);
        duoEnrollHandler    = new DuoEnrollHandler(duoRestTemplate);
        duoMiscHandler      = new DuoMiscHandler(duoRestTemplate);

        Assert.notNull(duoAuthHandler, "Configuration error");
        Assert.notNull(duoEnrollHandler, "Configuration error");
        Assert.notNull(duoMiscHandler, "Configuration error");
    }

    /**
     * TODO: to be removed
     */
    public void dummy() {
        Assert.notNull(duoAuthHandler, "Configuration error");
        Assert.notNull(duoEnrollHandler, "Configuration error");
        Assert.notNull(duoMiscHandler, "Configuration error");
    }
}
