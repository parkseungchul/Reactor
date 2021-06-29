package com.psc.sample.reactor002;

import com.psc.sample.reactor002.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
public class Reactor002Application {

    @Autowired
    MongoOperations mongoOperations;

    public static void main(String[] args) {
        SpringApplication.run(Reactor002Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        Item item1 = new Item("lego", "made in usa", 20.00);
        Item item2 = new Item("lego", "made in china", 10.00);
        Item item3 = new Item("rc car", "made in usa", 40.00);
        Item item4 = new Item("rc car", "made in china", 20.00);
        Item item5 = new Item("rc car", "made in india", 15.00);
        Item item6 = new Item("drone", "made in korea", 100.00);
        mongoOperations.save(item1);
        mongoOperations.save(item2);
        mongoOperations.save(item3);
        mongoOperations.save(item4);
        mongoOperations.save(item5);
        mongoOperations.save(item6);

    }


}
