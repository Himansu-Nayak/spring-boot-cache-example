//https://dzone.com/articles/spring-cache-annotation-tips-and-tricks
package com.org.cache.service.impl;

import com.org.cache.entity.Customer;
import com.org.cache.repository.CustomerRepository;
import com.org.cache.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.org.cache.config.CacheManagerConfig.CUSTOMER_CACHE;
import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CustomerRepository customerRepository;

    // this method configuration is equivalent to xml configuration
    @Override
    public String getFirstName(final Customer customer) {
        return customer.getFirstName();
    }

    /**
     * The method returns the customer's address,
       only it doesn't find it the cache- addresses and directory.
     *
     * @param customer the customer
     * @return the address
     */
    @Override
    public String getLastName(final Customer customer) {
        return customer.getFirstName();
    }

    /**
     * The method returns the customer's address,
        but refreshes all the entries in the cache to load new ones.
     *
     * @param customer the customer
     * @return the address
     */
    @Override
    public String getLastName2(final Customer customer) {
        return customer.getLastName();
    }

    /**
     * The method returns the customer's address,
        but not before selectively evicting the cache as per specified paramters.
     *
     * @param customer the customer
     * @return the address
     */
    @Override
    public String getLastName3(final Customer customer) {
        return customer.getLastName();
    }

    /**
     * The method uses the class level cache to look up for entries.
     *
     * @param customer the customer
     * @return the address
     */
    @Override
    public String getLastName4(final Customer customer) {
        return customer.getLastName();
    }

    /**
     * The method selectively caches the results that meet the predefined criteria.
     *
     * @param customer the customer
     * @return the address
     */
    @Override
    public String getLastName5(final Customer customer) {
        return customer.getLastName();
    }

    @Override
    public void populateCustomerCache() {
        List<Customer> customers = customerRepository.findAll();
        if(!isEmpty(customers)) {
            Cache customerCache = getCache(CUSTOMER_CACHE);
            customerCache.put(customers.get(0).getId(), customers.get(0));
            customers.stream().forEach(i -> customerCache.put(i.getId(), i));
        }

    }

    @Override
    public List<Customer> findDistinctCustomer() {
        return customerRepository.findDistinctLastNameByOrderByCreatedAtDesc();
    }

    @Override
    public Customer getCustomer(String id) {
        ValueWrapper valueWrapper = getCache(CUSTOMER_CACHE).get(id);
        return valueWrapper != null ? (Customer) valueWrapper.get() : null;
    }

    private Cache getCache(String cache) {
        return cacheManager.getCache(cache);
    }
}
