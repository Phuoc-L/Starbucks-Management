package com.example.springstarbucksapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springstarbucksapi.model.StarbucksCard;
import com.example.springstarbucksapi.model.StarbucksOrder;

@SpringBootTest(classes = {StarbucksCard.class, StarbucksOrder.class})
class SpringStarbucksApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
