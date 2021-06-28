package com.psc.sample.reactor002.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Item {

    private @Id String id;
    private String name;
    private double price;

    public Item(String name, double price){
        this.name = name;
        this.price = price;
    }
}
