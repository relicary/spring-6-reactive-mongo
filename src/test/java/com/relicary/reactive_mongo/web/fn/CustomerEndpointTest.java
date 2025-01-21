package com.relicary.reactive_mongo.web.fn;

import com.relicary.reactive_mongo.domain.Customer;
import com.relicary.reactive_mongo.model.CustomerDTO;
import com.relicary.reactive_mongo.services.impl.CustomerServiceImplTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerEndpointTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(2)
    void testListCustomers() {
        webTestClient.get()
                .uri(CustomerRouterConfig.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").value(greaterThan(1));
    }
    
    @Test
    @Order(1)
    void testGetCustomerById() {

        CustomerDTO customerDTO = getSavedTestCustomer();

        webTestClient.get()
                .uri(CustomerRouterConfig.CUSTOMER_PATH_ID, customerDTO.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CustomerDTO.class);
    }

    @Test
    void testGetCustomerByIdNotFound() {
        webTestClient.get()
                .uri(CustomerRouterConfig.CUSTOMER_PATH_ID, Integer.MAX_VALUE)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CustomerDTO.class);
    }

    @Test
    void testCreateCustomer() {
        CustomerDTO customerDTO = getSavedTestCustomer();

        webTestClient.post()
                .uri(CustomerRouterConfig.CUSTOMER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customerDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }

    @Test
    void testCreateCustomerBadData() {
        Customer customer = CustomerServiceImplTest.getTestCustomer();
        customer.setCustomerName("");

        webTestClient.post().uri(CustomerRouterConfig.CUSTOMER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customer)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    void testUpdateCustomer() {
        CustomerDTO customerDTO = getSavedTestCustomer();
        customerDTO.setCustomerName("Jane Doe");

        webTestClient.put()
                .uri(CustomerRouterConfig.CUSTOMER_PATH_ID, customerDTO.getId())
                .bodyValue(customerDTO)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testUpdateCustomerNotFound() {

        Customer customer = CustomerServiceImplTest.getTestCustomer();

        webTestClient.put()
                .uri(CustomerRouterConfig.CUSTOMER_PATH_ID, Integer.MAX_VALUE)
                .bodyValue(customer)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(4)
    void testUpdateCustomerBadRequest() {
        CustomerDTO customerDTO = getSavedTestCustomer();
        customerDTO.setCustomerName("");

        webTestClient.put()
                .uri(CustomerRouterConfig.CUSTOMER_PATH_ID, customerDTO)
                .bodyValue(customerDTO)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testPatchIdFound() {
        CustomerDTO customerDTO = getSavedTestCustomer();

        webTestClient.patch()
                .uri(CustomerRouterConfig.CUSTOMER_PATH_ID, customerDTO.getId())
                .bodyValue(customerDTO)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testPatchIdNotFound() {
        webTestClient.patch()
                .uri(CustomerRouterConfig.CUSTOMER_PATH_ID, Integer.MAX_VALUE)
                .body(Mono.just(CustomerServiceImplTest.getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testPatchCustomerBadData() {
        CustomerDTO customerDTO = getSavedTestCustomer();
        customerDTO.setCustomerName("");

        webTestClient.patch().uri(CustomerRouterConfig.CUSTOMER_PATH_ID, Integer.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customerDTO)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testDeleteCustomer() {
        CustomerDTO customerDTO = getSavedTestCustomer();

        webTestClient.delete()
                .uri(CustomerRouterConfig.CUSTOMER_PATH_ID, customerDTO.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteNotFound() {
        webTestClient.delete()
                .uri(CustomerRouterConfig.CUSTOMER_PATH_ID, Integer.MAX_VALUE)
                .exchange()
                .expectStatus().isNotFound();
    }

    public CustomerDTO getSavedTestCustomer(){
        FluxExchangeResult<CustomerDTO> customerDTOFluxExchangeResult = webTestClient.post()
                .uri(CustomerRouterConfig.CUSTOMER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(CustomerServiceImplTest.getTestCustomer())
                .exchange()
                .returnResult(CustomerDTO.class);

        List<String> location = customerDTOFluxExchangeResult.getResponseHeaders().get(HttpHeaders.LOCATION);

        return webTestClient.get().uri(CustomerRouterConfig.CUSTOMER_PATH)
                .exchange().returnResult(CustomerDTO.class).getResponseBody().blockFirst();
    }
}