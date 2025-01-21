package com.relicary.reactive_mongo.services.impl;

import com.relicary.reactive_mongo.mappers.CustomerMapper;
import com.relicary.reactive_mongo.model.CustomerDTO;
import com.relicary.reactive_mongo.repositories.CustomerRepository;
import com.relicary.reactive_mongo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Override
    public Flux<CustomerDTO> listCustomers() {
        return customerRepository
                .findAll()
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> findFirstByCustomerName(String customerName) {
        return customerRepository
                .findFirstByCustomerName(customerName)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> getById(String customerId) {
        return customerRepository
                .findById(customerId)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> saveCustomer(Mono<CustomerDTO> customerDTO) {
        return customerDTO
                .map(customerMapper::customerDtoToCustomer)
                .flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> saveCustomer(CustomerDTO customerDTO) {
        return customerRepository
                .save(customerMapper.customerDtoToCustomer(customerDTO))
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(String customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    customer.setCustomerName(customerDTO.getCustomerName());
                    return customer;
                })
                .flatMap(
                        customerRepository::save
                )
                .map(
                        customerMapper::customerToCustomerDto
                );
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(String customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(beer -> {
                    if(StringUtils.hasText(customerDTO.getCustomerName())){
                        beer.setCustomerName(customerDTO.getCustomerName());
                    }
                    return beer;
                })
                .flatMap(
                        customerRepository::save
                )
                .map(
                        customerMapper::customerToCustomerDto
                );
    }

    @Override
    public Mono<Void> deleteCustomerById(String customerId) {
        return customerRepository.deleteById(customerId);
    }
}
