package com.example.order_jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private Long userId;
    private Long productId;
    private Integer orderQuantity;
}
