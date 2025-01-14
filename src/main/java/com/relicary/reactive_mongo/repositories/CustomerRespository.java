package com.relicary.reactive_mongo.repositories;

import com.relicary.reactive_mongo.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRespository extends ReactiveMongoRepository<Customer, String> {
}
