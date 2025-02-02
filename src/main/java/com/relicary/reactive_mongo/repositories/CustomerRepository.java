package com.relicary.reactive_mongo.repositories;

import com.relicary.reactive_mongo.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Mono<Customer> findFirstByCustomerName(String customerName);
}
