package com.org.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import io.prometheus.client.cache.caffeine.CacheMetricsCollector;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheManagerConfig {

    public static final String CACHE_1 = "Test1" + UUID.randomUUID().toString();
    public static final String CACHE_2 = "Test2" + UUID.randomUUID().toString();

    @Bean
    public CacheManager cacheManager() {
        String specAsString = "initialCapacity=100,maximumSize=500,expireAfterAccess=5m,recordStats";
        CacheMetricsCollector cacheMetrics = new CacheMetricsCollector().register();
        cacheMetrics.addCache(CACHE_1, caffeineCacheBuilder().build());

        CaffeineCacheManager cacheManager = new CaffeineCacheManager(CACHE_1, CACHE_2);
        cacheManager.setAllowNullValues(false);
        //cacheManager.setCacheSpecification(specAsString);
        //cacheManager.setCaffeineSpec(caffeineSpec());
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    CaffeineSpec caffeineSpec() {
        return CaffeineSpec.parse
                ("initialCapacity=100,maximumSize=500,expireAfterAccess=5m,recordStats");
    }

    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(150)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .weakKeys()
                .removalListener(new CacheKeyRemovalListener())
                .recordStats();
    }

    class CacheKeyRemovalListener implements RemovalListener<Object, Object> {

        @Override
        public void onRemoval(Object key, Object value, RemovalCause cause) {
            System.out.format("removal listerner called with key [%s], cause [%s], evicted [%S]\n",
                    key, cause.toString(), cause.wasEvicted());
        }
    }

}
