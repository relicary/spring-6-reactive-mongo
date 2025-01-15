package com.relicary.reactive_mongo.services.impl;

import com.relicary.reactive_mongo.domain.Beer;
import com.relicary.reactive_mongo.mappers.BeerMapper;
import com.relicary.reactive_mongo.mappers.BeerMapperImpl;
import com.relicary.reactive_mongo.model.BeerDTO;
import com.relicary.reactive_mongo.services.BeerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Slf4j
class BeerServiceImplTest {

    @Autowired
    BeerService beerService;

    @Autowired
    BeerMapper beerMapper;

    BeerDTO  beerDTO;

    @BeforeEach
    void setUp() {
        beerDTO = beerMapper.beerToBeerDto(getTestBeer());
    }

    @Test
    @DisplayName("Test Save Beer Using Subscriber")
    void saveBeerUseSubscriber() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AtomicReference<BeerDTO> atomicBeerDto = new AtomicReference<>();

        Mono<BeerDTO> savedMono = beerService.saveBeer(Mono.just(getTestBeerDto()));

        savedMono.subscribe(savedDto -> {
            log.info(savedDto.getId());
            atomicBoolean.set(true);
            atomicBeerDto.set(savedDto);
        });

        await().untilTrue(atomicBoolean);

        BeerDTO persistedDto = atomicBeerDto.get();
        assertThat(persistedDto).isNotNull();
        assertThat(persistedDto.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test Save Beer Using Block")
    void testSaveBeerUseBlock() {
        BeerDTO savedDto = beerService.saveBeer(Mono.just(getTestBeerDto())).block();
        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test Update Using Reactive Streams")
    void testUpdateStreaming() {
        final String newName = "New Beer Name";

        AtomicReference<BeerDTO> atomicBeerDto = new AtomicReference<>();

        beerService
                .saveBeer(Mono.just(getSavedBeerDto()))
                .map(savedBeerDto -> {
                   savedBeerDto.setBeerName(newName);
                   return savedBeerDto;
                })
                .flatMap(beerService::saveBeer)
                .flatMap(savedUpdatedBeerDto -> beerService.getById(savedUpdatedBeerDto.getId()))
                .subscribe(atomicBeerDto::set);

        await().until(() -> atomicBeerDto.get() != null);
        assertThat(atomicBeerDto.get().getBeerName()).isEqualTo(newName);

    }

    @Test
    void testDeleteBeer() {
        BeerDTO beerToDelete = getSavedBeerDto();

        beerService.deleteBeerById(beerToDelete.getId()).block();

        BeerDTO emptyBeer = beerService.getById(beerToDelete.getId()).block();

        assertThat(emptyBeer).isNull();
    }

    public BeerDTO getSavedBeerDto() {
        return beerService
                .saveBeer(
                        Mono.just(getTestBeerDto())
                )
                .block();
    }

    public static BeerDTO getTestBeerDto() {
        return new BeerMapperImpl().beerToBeerDto(getTestBeer());
    }

    public static Beer getTestBeer() {
        return Beer.builder()
                .beerName("Space Dust")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .quantityOnHand(12)
                .upc("123213")
                .build();
    }

}