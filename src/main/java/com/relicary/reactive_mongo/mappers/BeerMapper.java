package com.relicary.reactive_mongo.mappers;

import com.relicary.reactive_mongo.domain.Beer;
import com.relicary.reactive_mongo.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    BeerDTO beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDTO beerDTO);
}
