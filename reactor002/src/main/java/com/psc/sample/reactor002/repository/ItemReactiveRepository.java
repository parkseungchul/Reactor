package com.psc.sample.reactor002.repository;

import com.psc.sample.reactor002.domain.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ItemReactiveRepository extends ReactiveCrudRepository<Item, String> {

    Flux<Item> findByNameContaining(String itemName);

}
