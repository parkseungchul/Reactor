package com.psc.sample.reactor.service;

public class TestFoodService001 {
    public static void main(String[] args) {

        FoodServer simpleServer = new FoodServer(new FoodMake1());

        // 리엑터는 lazy 이며 구독하기 전까지는 아무일도 일어나지 않음
        simpleServer.getFood1().subscribe(food -> {
            System.out.println(food);
        });
        System.out.println("-----");

        simpleServer.getFoods1().subscribe(food -> {
            System.out.println(food);
        });
        System.out.println("-----");

        simpleServer.getFoods2().subscribe(food -> {
            System.out.println(food);
        });
        System.out.println("-----");

    }
}
