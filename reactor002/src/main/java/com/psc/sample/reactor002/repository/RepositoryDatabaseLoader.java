package com.psc.sample.reactor002.repository;

import com.psc.sample.reactor002.domain.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class RepositoryDatabaseLoader {

    @Bean
    CommandLineRunner initialize(MongoOperations mongoOperations){
        return args -> {
            mongoOperations.save(new Item("lego", 20.00));
            mongoOperations.save(new Item("rc car", 40.00));

        };
    }
}
