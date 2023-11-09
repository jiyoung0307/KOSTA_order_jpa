package com.example.order_jpa.entity;

import com.example.order_jpa.exception.NoEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(length = 45)
    private String name;
    private int price;
    private int quantity;

    public void decreaseQuantity(int orderQuantity) throws NoEnoughStockException {
        int remQuantity = this.quantity - orderQuantity;
        if (remQuantity < 0) {
            throw new NoEnoughStockException("재고수량이 부족합니다.");
        }
        this.quantity = remQuantity;
    }

    public void increaseQuantity(int cancelQuantity) {
        this.quantity += cancelQuantity;
    }
}