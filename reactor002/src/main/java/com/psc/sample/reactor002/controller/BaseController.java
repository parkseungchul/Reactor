package com.psc.sample.reactor002.controller;

import com.psc.sample.reactor002.domain.Cart;
import com.psc.sample.reactor002.domain.CartItem;
import com.psc.sample.reactor002.repository.CartReactiveRepository;
import com.psc.sample.reactor002.repository.ItemReactiveRepository;
import com.psc.sample.reactor002.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
public class BaseController {

    private ItemReactiveRepository itemReactiveRepository;
    private CartReactiveRepository cartReactiveRepository;
    private CartService cartService;

    @GetMapping
    Mono<Rendering> base() {
        return Mono.just(Rendering.view("base.html")
                .modelAttribute("items",
                        this.itemReactiveRepository.findAll())
                .modelAttribute("cart",
                        this.cartReactiveRepository.findById("My Cart")
                                .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }

    @PostMapping("/add/{id}")
    Mono<String> addToCart2(@PathVariable String id) {
        return cartService.addToCart("My Cart", id).thenReturn("redirect:/");
    }

    @PostMapping("/addC/{id}")
    Mono<String> addToCart1(@PathVariable String id){
        return cartReactiveRepository.findById("My Cart")  // cart 레파지토리에 My Cart 가 있다면
                .defaultIfEmpty(new Cart("My Cart"))  // 없다면 리액터 기법으로 없다면 생성
                .flatMap(cart -> cart.getCartItems().stream() // 카트의 아이템 리스트를 보고
                        .filter(cartItem -> cartItem.getItem() // 클릭한 것과 비교해보면
                                .getId().equals(id))
                        .findAny()  // 값이 있다면
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        })
                        .orElseGet(() -> {
                            return itemReactiveRepository.findById(id) // item 레파지토리에
                                    .map(item -> new CartItem(item)) // 새로운 카트 아이템에 생성 처음이니 개수 한개
                                    .map(cartItem -> {
                                        cart.getCartItems().add(cartItem);
                                        return cart;
                                    });
                        })).flatMap(cart -> cartReactiveRepository.save(cart)) // map vs flatMap 은 전혀 다른 걸로 변경 만능키
                .thenReturn("redirect:/");
    }

    @DeleteMapping("/delCartCount/{id}")
    Mono<String> delToCartCount(@PathVariable String id) {
        return cartService.delToCartCount("My Cart", id).thenReturn("redirect:/");
    }

    @DeleteMapping("/delCartAll/{id}")
    Mono<String> delToCartAll(@PathVariable String id) {
        return cartService.delToCartAll("My Cart", id).thenReturn("redirect:/");
    }




    @DeleteMapping("/delItem/{id}")
    Mono<String> deleteItem(@PathVariable String id) {
        return this.itemReactiveRepository.deleteById(id) //
                .thenReturn("redirect:/");
    }



}
