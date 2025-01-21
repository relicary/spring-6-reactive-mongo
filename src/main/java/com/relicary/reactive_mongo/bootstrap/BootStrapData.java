package com.relicary.reactive_mongo.bootstrap;

import com.relicary.reactive_mongo.domain.Beer;
import com.relicary.reactive_mongo.domain.Customer;
import com.relicary.reactive_mongo.repositories.BeerRepository;
import com.relicary.reactive_mongo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        beerRepository.deleteAll()
                .doOnSuccess(success -> {
                    loadBeerData();
                    loadCustomerData();
                })
                .subscribe();
    }

    private void loadBeerData() {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {

                Beer beer1 = Beer.builder()
                        .beerName("Galaxy Cat")
                        .beerStyle("Pale Ale")
                        .upc("12356")
                        .price(new BigDecimal("12.99"))
                        .quantityOnHand(122)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Beer beer2 = Beer.builder()
                        .beerName("Crank")
                        .beerStyle("Pale Ale")
                        .upc("12356222")
                        .price(new BigDecimal("11.99"))
                        .quantityOnHand(392)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Beer beer3 = Beer.builder()
                        .beerName("Sunshine City")
                        .beerStyle("IPA")
                        .upc("12356")
                        .price(new BigDecimal("13.99"))
                        .quantityOnHand(144)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                beerRepository.save(beer1).subscribe(b -> log.info(b.toString()));
                beerRepository.save(beer2).subscribe(b -> log.info(b.toString()));
                beerRepository.save(beer3).subscribe(b -> log.info(b.toString()));
            }
        });
    }

    private void loadCustomerData() {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {

                Customer customer1 = Customer.builder()
                        .customerName("Alicia Argento")
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Customer customer2 = Customer.builder()
                        .customerName("Benito Borrego")
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Customer customer3 = Customer.builder()
                        .customerName("Carlos Cerezo")
                        .build();

                customerRepository.save(customer1).subscribe(b -> log.info(b.toString()));
                customerRepository.save(customer2).subscribe(b -> log.info(b.toString()));
                customerRepository.save(customer3).subscribe(b -> log.info(b.toString()));
            }
        });
    }

}