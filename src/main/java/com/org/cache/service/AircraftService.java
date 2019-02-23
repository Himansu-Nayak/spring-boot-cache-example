package com.org.cache.service;

import com.org.cache.ProductRepository;
import com.org.cache.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = {"AIRCRAFTS"})
public class AircraftService {

    private static final Logger logger = LoggerFactory.getLogger(AircraftService.class);
    private final ProductRepository template;
    private final String mongoCollection = "Product";

    @Autowired
    public AircraftService(ProductRepository template) {
        this.template = template;
    }

    @Cacheable(unless = "#result == null")
    public Product getAircraftByModelName(String modelName) {
        logger.info("Executing getAircraftByModelName for model:{} \tCache MISS!", modelName);
        slowDownForDemo();
//        Query query = new Query(Critzeria.where("model").is(modelName));
//        return template.findOne(query, Product.class, mongoCollection);
        return null;
    }

    @CachePut(key = "#Product.model", condition = "#Product.topSpeed > 0", unless = "#result == null")
    public Product createAircraft(Product Product) {
        logger.info("Executing createAircraft, model:{}", Product.getDescription());
//        try {
//            template.insert(Product, mongoCollection);
//        } catch (DuplicateKeyException dke) {
//            //OK for demo
//        }
//        return template.findOne(
//                new Query(Criteria.where("model").is(Product.getModel())),
//                Product.class, mongoCollection);//has id
        return null;
    }

    @CacheEvict(key = "#Product.model")
    public void updateAircraft(Product Product) {
        logger.info("Executing updateAircraft, model:{} topSpeed: {} Cache Evict!",
                Product.getDescription(), Product.getPrice());
        try {
//            template.save(Product, mongoCollection);
        } catch (Exception dke) {
            //OK for demo
        }
    }

    @CacheEvict(key = "#Product.model")
    public void removeAircraft(Product Product) {
        logger.info("Executing removeAircraft, model:{} \tCache Evict!", Product.getDescription());
//        template.remove(Product, mongoCollection);
    }

    @Caching(evict = {
            @CacheEvict(value = "AIRCRAFTS", allEntries = true),
            @CacheEvict(value = "SECOND_CACHE", allEntries = true)
    })
    public void clearAllCaches() {
        logger.info("Cleared all caches");
    }


    private void slowDownForDemo() {
        try {
            Thread.sleep(2000L);
        } catch (Exception e) {
        }
    }

}
