package com.psc.sample.reactor002.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private @Id String id;
    private String name;
    private String description;
    private double price;

    public Item(String name, String description, double price){
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
