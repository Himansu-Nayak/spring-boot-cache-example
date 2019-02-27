package com.org.cache.repository;

import com.org.cache.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    List<Customer> findDistinctLastNameByOrderByCreatedAtDesc();
}
