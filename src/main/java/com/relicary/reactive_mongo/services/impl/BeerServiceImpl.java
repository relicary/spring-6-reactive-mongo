package com.relicary.reactive_mongo.services.impl;

import com.relicary.reactive_mongo.mappers.BeerMapper;
import com.relicary.reactive_mongo.model.BeerDTO;
import com.relicary.reactive_mongo.repositories.BeerRepository;
import com.relicary.reactive_mongo.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository
                .findAll()
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> findFirstByBeerName(String beerName) {
        return beerRepository
                .findFirstByBeerName(beerName)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Flux<BeerDTO> findByBeerStyle(String beerStyle) {
        return beerRepository
                .findByBeerStyle(beerStyle)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> getById(String beerId) {
        return beerRepository
                .findById(beerId)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDTO) {
        return beerDTO
                .map(beerMapper::beerDtoToBeer)
                .flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> saveBeer(BeerDTO beerDTO) {
        return beerRepository
                .save(beerMapper.beerDtoToBeer(beerDTO))
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(String beerId, BeerDTO beerDTO) {
        return null;
    }

    @Override
    public Mono<BeerDTO> patchBeer(String beerId, BeerDTO beerDTO) {
        return null;
    }

    @Override
    public Mono<Void> deleteBeerById(String beerId) {
        return beerRepository.deleteById(beerId);
    }
}
