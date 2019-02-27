package com.org.cache.repository;

import com.org.cache.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query(value = "SELECT m.* " +
            "FROM customer m " +
            "LEFT JOIN customer b " +
                "ON m.last_name = b.last_name " +
                "AND m.created_at < b.created_at " +
            "WHERE b.created_at IS NULL", nativeQuery = true)
     List<Customer> findDistinctCustomer();
}
