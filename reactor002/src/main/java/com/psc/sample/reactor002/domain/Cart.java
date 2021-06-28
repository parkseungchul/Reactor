package com.psc.sample.reactor002.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private @Id String id;
    private List<CartItem> cartItems;

    public Cart(String id){
        this(id, new ArrayList<>());
    }

    public void removeItem(CartItem cartItem){
        this.cartItems.remove(cartItem);
    }

}
