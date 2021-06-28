package com.psc.sample.reactor002.repository;

import com.psc.sample.reactor002.domain.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CartReactiveRepository extends ReactiveCrudRepository<Cart, String> {
}
