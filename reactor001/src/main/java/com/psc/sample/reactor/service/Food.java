package com.psc.sample.reactor.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Food {

    private String description;
    private boolean delivered = false;

    public static Food status(Food dish){
        Food deliveredFood = new Food(dish.description, true);
        return deliveredFood;
    }


}
