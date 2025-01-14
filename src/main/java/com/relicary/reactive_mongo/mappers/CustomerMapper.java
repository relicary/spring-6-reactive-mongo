package com.relicary.reactive_mongo.mappers;

import com.relicary.reactive_mongo.domain.Customer;
import com.relicary.reactive_mongo.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    
    CustomerDTO customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDTO customerDTO);
}
