package com.relicary.reactive_mongo.services;

import com.relicary.reactive_mongo.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<CustomerDTO> listCustomers();

    Mono<CustomerDTO> findFirstByCustomerName(String customerName);

    Mono<CustomerDTO> getById(String customerId);

    Mono<CustomerDTO> saveCustomer(Mono<CustomerDTO> CustomerDTO);

    Mono<CustomerDTO> saveCustomer(CustomerDTO CustomerDTO);

    Mono<CustomerDTO> updateCustomer(String customerId, CustomerDTO CustomerDTO);

    Mono<CustomerDTO> patchCustomer(String customerId, CustomerDTO CustomerDTO);

    Mono<Void> deleteCustomerById(String customerId);
}
