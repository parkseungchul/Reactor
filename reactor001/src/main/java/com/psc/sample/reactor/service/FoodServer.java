package com.psc.sample.reactor.service;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class FoodServer {
    final FoodMake1 foodService;

    public Mono<Food> getFood1(){
        return foodService.getFood().map(food -> Food.status(food));
    }

    public Flux<Food> getFoods1(){
        return this.foodService.getFoods().map(food -> Food.status(food));
    };

    public Flux<Food> getFoods2(){
        return this.foodService.getFoods()
                .doOnNext(food -> System.out.println("Thank you for order [" + food.getDescription() + "]"))
                .doOnError(error -> System.out.println(error.getMessage()))
                .doOnComplete(() -> System.out.println("Server End"))
                //.map(Food::deliver);
                .map( food -> Food.status(food));
    }
}
