package com.org.cache.config;

import com.github.benmanes.caffeine.cache.*;
import io.prometheus.client.cache.caffeine.CacheMetricsCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class CacheManagerConfig {

    public static final String CUSTOMER_CACHE = "customer_cache_" + UUID.randomUUID().toString();
    public static final String CACHE_2 = "Test";

    @Bean
    public CacheManager cacheManager() {
        String specAsString = "initialCapacity=100,maximumSize=500,expireAfterAccess=5m,recordStats";
        CacheMetricsCollector cacheMetrics = new CacheMetricsCollector().register();
        cacheMetrics.addCache(CUSTOMER_CACHE, customerCache());
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(CUSTOMER_CACHE, CACHE_2);
        cacheManager.setAllowNullValues(false);
        //cacheManager.setCacheSpecification(specAsString);
        //cacheManager.setCaffeineSpec(caffeineSpec());
        //cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    @Bean
    public CaffeineCache caffeineCustomerCache() {
        return new CaffeineCache(CUSTOMER_CACHE, customerCache());
    }

    @Bean
    Cache<Object, Object> customerCache() {
        return caffeineCacheBuilder().build();
    }

    @Bean
    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(10000)
                .expireAfterAccess(1, TimeUnit.DAYS)
                .removalListener(new CacheKeyRemovalListener())
                .recordStats();
    }

    CaffeineSpec caffeineSpec() {
        return CaffeineSpec.parse
                ("initialCapacity=100,maximumSize=500,expireAfterAccess=5m,recordStats");
    }

    class CacheKeyRemovalListener implements RemovalListener<Object, Object> {

        @Override
        public void onRemoval(Object key, Object value, RemovalCause cause) {
            log.info("key [ {} ] was evicted [ {} ] due to [ {} ]", key, cause.wasEvicted(), cause.toString());
        }
    }

}
