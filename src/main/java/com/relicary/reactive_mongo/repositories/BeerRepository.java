package com.relicary.reactive_mongo.repositories;

import com.relicary.reactive_mongo.domain.Beer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BeerRepository extends ReactiveMongoRepository<Beer, String> {
}
