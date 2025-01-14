package com.relicary.reactive_mongo.services.impl;

import com.relicary.reactive_mongo.domain.Beer;
import com.relicary.reactive_mongo.model.BeerDTO;
import com.relicary.reactive_mongo.services.BeerService;
import reactor.core.publisher.Mono;

public class BeerServiceImpl implements BeerService {

    @Override
    public Mono<Beer> saveBeer(BeerDTO beerDTO) {
        return null;
    }

    @Override
    public Mono<Beer> getById(String beerId) {
        return null;
    }
}
