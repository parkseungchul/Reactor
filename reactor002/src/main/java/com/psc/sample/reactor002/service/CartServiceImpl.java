package com.psc.sample.reactor002.service;

import com.psc.sample.reactor002.domain.Cart;
import com.psc.sample.reactor002.domain.Item;
import com.psc.sample.reactor002.domain.vo.CartItem;
import com.psc.sample.reactor002.repository.CartReactiveRepository;
import com.psc.sample.reactor002.repository.ItemReactiveRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

@Service
public class CartServiceImpl implements CartService{
    private final ItemReactiveRepository itemReactiveRepository;
    private final CartReactiveRepository cartReactiveRepository;

    public CartServiceImpl(
            ItemReactiveRepository itemReactiveRepository,
            CartReactiveRepository cartReactiveRepository) {
        this.itemReactiveRepository = itemReactiveRepository;
        this.cartReactiveRepository = cartReactiveRepository;
    }

    public Flux<Item> itemSearchName(String name, String description, boolean isSuit){
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (isSuit
                ? ExampleMatcher.matchingAll().withIgnorePaths("price")
                : ExampleMatcher.matching()
                .withMatcher("name", contains())
                .withMatcher("description", contains())
                .withIgnoreCase()
                .withIgnorePaths("price"));

        Example<Item> probe = Example.of(item, matcher); // <6>

        Example<Item> itemExample = Example.of(item, matcher);
        return itemReactiveRepository.findAll(itemExample);
    }


    public Mono<Cart> delToCartCount(String cartId, String id) {
        return cartReactiveRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId))
                .flatMap(
                        cart -> cart.getCartItems().stream()
                                .filter(cartItem -> cartItem.getItem().getId().equals(id)).findAny()
                        .map(cartItem -> {
                            if(cartItem.isOne()){
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

