package com.psc.sample.reactor002.repository;

import com.psc.sample.reactor002.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class RepositoryTest {

    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    @Autowired
    CartReactiveRepository cartReactiveRepository;

    @BeforeEach
    void setUp(){
        StepVerifier.create(
                itemReactiveRepository.deleteAll()
        ).verifyComplete();

        Item item1 = new Item("lego", 20.00);
        Item item2 = new Item("rc car", 40.00);

        StepVerifier.create(
                itemReactiveRepository.save(item1)
        ).expectNextMatches(item -> {
            assertThat(item.getId()).isNotNull();
            assertThat(item.getName()).isEqualTo("lego");
            assertThat(item.getPrice()).isEqualTo(20.00);
            return true;
        }).verifyComplete();

        StepVerifier.create(
                itemReactiveRepository.save(item2)
        ).expectNextMatches(item -> {
            assertThat(item.getId()).isNotNull();
            assertThat(item.getName()).isEqualTo("rc car");
            assertThat(item.getPrice()).isEqualTo(40.00);
            return true;
        }).verifyComplete();
    }

    @Test
    public void itemRepositoryCount(){
        StepVerifier.create(
                itemReactiveRepository.count()
        ).expectNextMatches(cnt ->{
            assertThat(cnt).isEqualTo(2L);
            return true;
        }).verifyComplete();
    }

    @Test
    public void itemRepositoryFindName(){
        StepVerifier.create(
                itemReactiveRepository.findByNameContaining("rc")
        ).expectNextMatches(item -> {
            assertThat(item.getId()).isNotNull();
            assertThat(item.getName()).isEqualTo("rc car");
            assertThat(item.getPrice()).isEqualTo(40.00);
            return true;
        }).verifyComplete();

    }
}
