package com.example.order_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderJpaApplication.class, args);
        System.out.println("여기가 메인이다");
    }

}
