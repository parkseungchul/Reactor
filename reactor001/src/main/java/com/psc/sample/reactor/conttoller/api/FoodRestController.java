package com.psc.sample.reactor.conttoller.api;

import com.psc.sample.reactor.service.Food;
import com.psc.sample.reactor.service.FoodMake2;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class FoodRestController {

    FoodMake2 foodMakeService2;

    @GetMapping(value = "/makeFoods", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Food> makeFoods(){
        return foodMakeService2.getFoods();
    }

    @GetMapping(value = "/deliveredFoods", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Food> deliveredFoods(){
        return foodMakeService2.getFoods().map(food -> Food.status(food));
    }

    @GetMapping(value = "/deliveredFoods2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Food> deliveredFoods2(){
        return makeFoods().map(food -> Food.status(food));
    }

}
