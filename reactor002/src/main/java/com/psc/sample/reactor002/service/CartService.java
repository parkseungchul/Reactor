package com.psc.sample.reactor002.service;

import com.psc.sample.reactor002.domain.Cart;
import com.psc.sample.reactor002.domain.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartService {

    public Flux<Item> itemSearchName(String name, String description, boolean isSuit);

    public Mono<Cart> delToCartCount(String cartId, String id);

    public Mono<Cart> delToCartAll(String cartId, String id);

    public Mono<Cart> addToCart(String cartId, String id);
}

