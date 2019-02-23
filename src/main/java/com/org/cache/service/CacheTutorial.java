package com.org.cache.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.org.cache.entity.Product;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CacheTutorial implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CacheTutorial.class);
    private final AircraftService aircraftService;
    private final CacheManager cacheManager;

    @Autowired
    public CacheTutorial(AircraftService dataService, CacheManager cacheManager) {
        this.aircraftService = dataService;
        this.cacheManager = cacheManager;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("================================================================");
        logger.info("Using cache Manager {}", cacheManager.getClass().getSimpleName());

        Product cessna = new Product("Cessna 650", new BigDecimal(478));
        Product aerostar = new Product("Aerostar PA-602P",  new BigDecimal(232));
        Product piper = new Product("Piper PA-31-300",  new BigDecimal(545));
//
//        //store in cache upon creation
//        aircraftService.createAircraft(cessna);
//        aircraftService.createAircraft(aerostar);
//        aircraftService.createAircraft(piper);
//
//        logger.info("Calling getAircraftByModelName() ...");
//        aircraftService.getAircraftByModelName(cessna.getDescription()); //hit
//        logger.info("Calling getAircraftByModelName() ...");
//        aircraftService.getAircraftByModelName(aerostar.getPrice());//hit
//        logger.info("Calling getAircraftByModelName() ...");
//        aircraftService.getAircraftByModelName(piper.getDescription());//hit
//
//        piper.setTopSpeed(200);

//        aircraftService.updateAircraft(piper);//evict
//        aircraftService.getAircraftByModelName(piper.getModel());//miss
//        aircraftService.getAircraftByModelName(piper.getModel());//hit
//
//        aircraftService.removeAircraft(piper);//evict
//        aircraftService.getAircraftByModelName(piper.getModel());//miss
//
//        aircraftService.clearAllCaches();//evict all caches
//        aircraftService.getAircraftByModelName(cessna.getModel()); //miss
//        aircraftService.getAircraftByModelName(aerostar.getModel());//miss
//
//        logger.info(getCoffeeCacheStats().toString());
        logger.info("================================================================");
    }


    public CacheStats getCoffeeCacheStats() {
        org.springframework.cache.Cache cache = cacheManager.getCache("AIRCRAFTS");
        Cache nativeCoffeeCache = (Cache) cache.getNativeCache();
        // System.out.println(nativeCoffeeCache.asMap().toString());
        return nativeCoffeeCache.stats();
    }

}
