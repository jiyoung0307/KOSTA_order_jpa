package com.example.order_jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateDTO {
    private Long productId;
    private String name;
    private int price;
}