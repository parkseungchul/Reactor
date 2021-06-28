package com.psc.sample.reactor002.repository;

import com.psc.sample.reactor002.domain.Cart;
import com.psc.sample.reactor002.domain.CartItem;
import com.psc.sample.reactor002.domain.Item;
import com.psc.sample.reactor002.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class TestRepository1 {


    @MockBean
    public ItemReactiveRepository itemReactiveRepository;

    @MockBean
    public CartReactiveRepository cartReactiveRepository;

    public CartService cartService;

    @BeforeEach
    public void setup(){
        Item item = new Item("lego",20.00);
        CartItem cartItem = new CartItem(item);
        Cart cart = new Cart("My Cart", Collections.singletonList(cartItem));
        when(itemReactiveRepository.findById(anyString())).thenReturn(Mono.just(item));
        when(cartReactiveRepository.save(any(Cart.class))).thenReturn(Mono.just(cart));
        cartService = new CartService(itemReactiveRepository, cartReactiveRepository);

    }

    @Test
    public void cartTest(){
        StepVerifier.create(
                this.cartService.addToCart("My Cart", "item"))
                        .expectNextMatches(cart -> {
                            assertThat(cart.getCartItems()).extracting(CartItem::getQuantity)
                                    .containsExactlyInAnyOrder(1);
                            return true;
                        }).verifyComplete();
    }




}
