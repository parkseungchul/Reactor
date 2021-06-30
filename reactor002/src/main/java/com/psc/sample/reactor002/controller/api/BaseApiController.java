package com.psc.sample.reactor002.controller.api;

import com.psc.sample.reactor002.domain.Item;
import com.psc.sample.reactor002.repository.ItemReactiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.http.HttpResponse;

@AllArgsConstructor
@RequestMapping("/api")
@RestController
public class BaseApiController {

    final ItemReactiveRepository itemReactiveRepository;

    @GetMapping("/items")
    public Flux<Item> getItems(){
        return itemReactiveRepository.findAll();
    }

    @GetMapping("/item/{id}")
    public Mono<Item> getItem(@PathVariable String id){
        return itemReactiveRepository.findById(id);
    }

    @PostMapping("/item")
    public Mono<ResponseEntity<?>> addItem(@RequestBody Mono<Item> item){
        return item.flatMap(inItem -> itemReactiveRepository.save(inItem))
                .map(outItem -> ResponseEntity.created(URI.create("/api/item/"+outItem.getId())).body(outItem));
    }

    @PutMapping("/item/{id}")
    public Mono<ResponseEntity<?>> updateItem(@RequestBody Mono<Item> item, @PathVariable String id){
        return item.map(
                inputItem -> new Item(id, inputItem.getName(), inputItem.getDescription(), inputItem.getPrice()))
                .flatMap(outItem -> itemReactiveRepository.save(outItem))
                .map(outItem2 -> ResponseEntity.ok(outItem2));
    }

    @DeleteMapping("/item/{id}")
    public Mono<String> deleteItem(@PathVariable String id){
        return itemReactiveRepository.findById(id)
                .defaultIfEmpty(new Item())
                .flatMap(item -> {
                    if(item.getId() == null){
                        return Mono.just("data not found");
                    }else{
                        itemReactiveRepository.delete(item);
                        return Mono.just("delete Success");
                    }
                });
    }
}
