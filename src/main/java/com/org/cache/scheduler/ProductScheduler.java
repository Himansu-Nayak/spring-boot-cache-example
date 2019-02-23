package com.org.cache.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ProductScheduler {

    @PostConstruct
    public void init() {
        log.info("populate the cache on startup");
    }

    @Scheduled()
    public void populate() {

    }

}
