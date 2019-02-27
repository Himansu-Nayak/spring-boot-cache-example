package com.org.cache.service;

import com.org.cache.entity.Customer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

public interface CustomerService {

    @Cacheable(value = "addresses", key = "#customer.id")
    String getFirstName(Customer customer);

    @Cacheable({ "addresses", "directory" })
    String getLastName(Customer customer);

    @CacheEvict(value = "addresses", allEntries = true)
    String getLastName2(Customer customer);

    @Caching(evict = { @CacheEvict("addresses"), @CacheEvict(value = "directory", key = "#customer.name") })
    String getLastName3(Customer customer);

    @Cacheable
    String getLastName4(Customer customer);

    @CachePut(value = "addresses", condition = "#customer.name=='Tom'")
 // @CachePut(value = "addresses", unless = "#result.length>64")
    String getLastName5(Customer customer);

    void populateCustomerCache();

    List<Customer> findDistinctCustomer();

    Customer getCustomer(String id);

}
