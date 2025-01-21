package com.relicary.reactive_mongo.services.impl;

import com.relicary.reactive_mongo.domain.Customer;
import com.relicary.reactive_mongo.mappers.BeerMapper;
import com.relicary.reactive_mongo.model.BeerDTO;
import com.relicary.reactive_mongo.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CustomerServiceImplTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    BeerMapper beerMapper;

    BeerDTO  beerDTO;


    public static Customer getTestCustomer() {
        return Customer.builder()
                .customerName("John Doe")
                .build();
    }

}