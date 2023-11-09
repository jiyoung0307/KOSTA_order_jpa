package com.example.order_jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)  // 지연로딩
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)  /** OrderProduct와 연관관계 */
    private List<OrderProduct> orderProduct = new ArrayList<OrderProduct>();

    @Column(name="order_date", length = 10)
    private String orderDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name="order_status", length = 10)
    private OrderStatus orderStatus;
    private long totalPrice;
    private int totalQuantity;
}