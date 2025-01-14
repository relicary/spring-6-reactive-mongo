package com.relicary.reactive_mongo.services;

import com.relicary.reactive_mongo.domain.Beer;
import com.relicary.reactive_mongo.model.BeerDTO;
import reactor.core.publisher.Mono;

public interface BeerService {

    Mono<Beer> saveBeer(BeerDTO beerDTO);

    Mono<Beer> getById(String beerId);
}