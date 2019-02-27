package com.org.cache.scheduler;

import com.org.cache.entity.Customer;
import com.org.cache.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CustomerScheduler {

    private static final Logger logger = LoggerFactory.getLogger(CustomerScheduler.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private CustomerService customerService;

    @PostConstruct
    public void init() {
        log.info("populate cache on startup");
        customerService.populateCustomerCache();
    }

    @Scheduled(fixedRate = 2000)
    public void scheduleTaskWithFixedRate() {
        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
        List<Customer> customers = customerService.findDistinctCustomer();
    }

    @Scheduled(fixedDelay = 2000)
    public void scheduleTaskWithFixedDelay() {
        logger.info("Fixed Delay Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            logger.error("Ran into an error {}", ex);
            throw new IllegalStateException(ex);
        }
    }

    @Scheduled(fixedRate = 2000, initialDelay = 5000)
    public void scheduleTaskWithInitialDelay() {
        Customer customer = customerService.getCustomer("7354d62d-a1ca-4794-bb64-b646184ad1d2");
        log.info("Customer 7354d62d-a1ca-4794-bb64-b646184ad1d2 fetch from cache [ {} ]", customer);
        logger.info("Fixed Rate Task with Initial Delay :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }

    @Scheduled(cron = "${cron.expression.customer}")
    public void scheduleTaskWithCronExpression() {
        logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        customerService.populateCustomerCache();
    }

}
