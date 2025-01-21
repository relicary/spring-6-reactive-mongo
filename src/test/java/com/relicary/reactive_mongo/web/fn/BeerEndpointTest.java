package com.relicary.reactive_mongo.web.fn;

import com.relicary.reactive_mongo.model.BeerDTO;
import com.relicary.reactive_mongo.services.impl.BeerServiceImplTest;
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
class BeerEndpointTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(2)
    void testListBeers() {
        webTestClient.get()
                .uri(BeerRouterConfig.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").value(greaterThan(1));
    }

    @Test
    @Order(1)
    void testGetBeerById() {

        BeerDTO beerDto = getSavedTestBeer();

        webTestClient.get()
                .uri(BeerRouterConfig.BEER_PATH_ID, beerDto.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(BeerDTO.class);
    }

    @Test
    void testCreateBeer() {
        BeerDTO beerDto = getSavedTestBeer();

        webTestClient.post()
                .uri(BeerRouterConfig.BEER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(beerDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }

    @Test
    @Order(3)
    void testUpdateBeer() {
        BeerDTO beerDTO = getSavedTestBeer();
        beerDTO.setBeerName("New");

        webTestClient.put()
                .uri(BeerRouterConfig.BEER_PATH_ID, beerDTO.getId())
                .bodyValue(beerDTO)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testPatchIdFound() {
        BeerDTO beerDTO = getSavedTestBeer();

        webTestClient.patch()
                .uri(BeerRouterConfig.BEER_PATH_ID, beerDTO.getId())
                .bodyValue(beerDTO)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteBeer() {
        BeerDTO beerDTO = getSavedTestBeer();

        webTestClient.delete()
                .uri(BeerRouterConfig.BEER_PATH_ID, beerDTO.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    public BeerDTO getSavedTestBeer(){
        FluxExchangeResult<BeerDTO> beerDTOFluxExchangeResult = webTestClient.post()
                .uri(BeerRouterConfig.BEER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(BeerServiceImplTest.getTestBeer()), BeerDTO.class)
                .exchange()
                .returnResult(BeerDTO.class);

        List<String> location = beerDTOFluxExchangeResult.getResponseHeaders().get(HttpHeaders.LOCATION);

        return webTestClient.get().uri(BeerRouterConfig.BEER_PATH)
                .exchange().returnResult(BeerDTO.class).getResponseBody().blockFirst();
    }
}