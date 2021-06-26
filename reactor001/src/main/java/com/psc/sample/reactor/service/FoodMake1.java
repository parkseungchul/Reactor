package com.psc.sample.reactor.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FoodMake1 {

    Flux<Food> getFoods(){
        return Flux.just(
                new Food("pizza", true),
                new Food("pasta", true),
                new Food("hamburger", true)
        );
    }

    Mono<Food> getFood(){
        return Mono.just(new Food("pizza", true));
    }
}
