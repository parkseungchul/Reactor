package com.psc.sample.reactor002.repository;

import com.psc.sample.reactor002.domain.Item;
import com.psc.sample.reactor002.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceTest {

    @Autowired
    CartService cartService;

    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    public Long itemCnt;

    @BeforeEach
    void setUp() {
        StepVerifier.create(
                itemReactiveRepository.deleteAll()
        ).verifyComplete();

        Item item1 = new Item("lego", "made in usa", 20.00);
        Item item2 = new Item("lego", "made in china", 10.00);
        Item item3 = new Item("rc car", "made in usa", 40.00);
        Item item4 = new Item("rc car", "made in china", 20.00);
        Item item5 = new Item("rc car", "made in india", 15.00);
        Item item6 = new Item("drone", "made in korea", 100.00);
        List<Item> itemList = Arrays.asList(item1, item2, item3, item4, item5, item6);

        itemCnt = Long.valueOf(itemList.size());
        StepVerifier.create(
                itemReactiveRepository.saveAll(itemList)
        ).expectNextMatches(item -> {
            return true;
        }).expectNextCount(itemCnt - 1).verifyComplete();
    }

    @Test
    public void itemSearchName(){
        StepVerifier.create(
                cartService.itemSearchName("drone", "made in korea", true)
        ).expectNextMatches(item -> {
            System.out.println(item.toString());
            return true;
        }).verifyComplete();
    }

    // 널일때는 다 된다...
    @Test
    public void itemSearchName2(){
        StepVerifier.create(
                cartService.itemSearchName("rc", "china",false)
        ).thenConsumeWhile(item -> {
            System.out.println(item.toString());
            return true;
        }).verifyComplete();

    }

}
