package com.example.order_jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
//    order 엔티티의 연관관계주인
    private Long orderProdcutId;

    @ManyToOne(fetch = FetchType.LAZY)  // 지연로딩
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)  // 지연로딩
    @JoinColumn(name = "product_id")
    private Product product;

    private long orderPrice;
    private int orderQuantity;
}