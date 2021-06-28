package com.psc.sample.reactor002.service;

import com.psc.sample.reactor002.domain.Cart;
import com.psc.sample.reactor002.domain.CartItem;
import com.psc.sample.reactor002.repository.CartReactiveRepository;
import com.psc.sample.reactor002.repository.ItemReactiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class CartService {
    private final ItemReactiveRepository itemReactiveRepository;
    private final CartReactiveRepository cartReactiveRepository;

    public CartService(ItemReactiveRepository itemReactiveRepository, CartReactiveRepository  cartReactiveRepository){
        this.itemReactiveRepository = itemReactiveRepository;
        this.cartReactiveRepository = cartReactiveRepository;
    }


    public Mono<Cart> delToCartCount(String cartId, String id) {
        return cartReactiveRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId))
                .flatMap(
                        cart -> cart.getCartItems().stream()
                                .filter(cartItem -> cartItem.getItem().getId().equals(id)).findAny()
                        .map(cartItem -> {
                            System.out.println(cartItem.toString());
                            if(cartItem.getQuantity() == 1){
                                cart.removeItem(cartItem);
                            }else{
                                cartItem.decrement();
                            }
                            return Mono.just(cart);
                        }).orElseGet(() -> {
                            return Mono.empty();
                        })).flatMap(cart ->  cartReactiveRepository.save(cart));
    }






    public Mono<Cart> delToCartAll(String cartId, String id) {
        return cartReactiveRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId))
                .flatMap(
                        cart -> cart.getCartItems().stream()
                                .filter(cartItem -> cartItem.getItem().getId().equals(id)).findAny()
                                .map(cartItem -> {
                                    cart.removeItem(cartItem);
                                    return Mono.just(cart);
                                }).orElseGet(() -> {
                                   return Mono.empty();
                                }))
                .flatMap(cart -> cartReactiveRepository.save(cart));
    }


    public Mono<Cart> addToCart(String cartId, String id){
        return cartReactiveRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId))
                .flatMap(
                        cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem().getId().equals(id)).findAny()
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        })
                        .orElseGet(() -> itemReactiveRepository.findById(id)  // 아이템이 없을 경우 findAny 다음 턴 아니냐
                                //.map(CartItem::new)
                                .map(cartItem -> new CartItem(cartItem))
                                .doOnNext(cartItem ->
                                    cart.getCartItems().add(cartItem))
                                .map(cartItem -> cart)))
                //.flatMap(cartReactiveRepository::save);
                  .flatMap(cart -> cartReactiveRepository.save(cart));  // 둘 다 저장하는거야

    }
}

