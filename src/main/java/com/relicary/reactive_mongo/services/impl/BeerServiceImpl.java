package com.relicary.reactive_mongo.services.impl;

import com.relicary.reactive_mongo.domain.Beer;
import com.relicary.reactive_mongo.mappers.BeerMapper;
import com.relicary.reactive_mongo.model.BeerDTO;
import com.relicary.reactive_mongo.repositories.BeerRepository;
import com.relicary.reactive_mongo.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;

    @Override
    public Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDTO) {
        return beerDTO
                .map(beerMapper::beerDtoToBeer)
                .flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<Beer> getById(String beerId) {
        return null;
    }
}
