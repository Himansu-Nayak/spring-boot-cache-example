package com.org.cache.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import static org.springframework.boot.context.config.ConfigFileApplicationListener.DEFAULT_ORDER;

@Slf4j
@Component
public class LoadedConfigFileListener implements ApplicationListener<ApplicationReadyEvent>, Ordered {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        MutablePropertySources prpSrc = event.getApplicationContext().getEnvironment().getPropertySources();
        prpSrc.iterator().forEachRemaining(i -> log.info("Successfully loaded [ {} ] into application context", i.getName()));
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER + 1;
    }
}