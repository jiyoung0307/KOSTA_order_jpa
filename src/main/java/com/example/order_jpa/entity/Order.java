package com.example.order_jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Column(name="order_date", length = 10)
    private LocalDateTime orderDate;
    @Enumerated(value = EnumType.STRING)
    @Column(name="order_status", length = 10)
    private OrderStatus orderStatus;
    private long totalPrice;
    private int totalQuantity;

    public void cancel(){
        this.setOrderStatus(OrderStatus.CANCELLED);
        for (OrderProduct orderProduct : this.orderProducts) {
            orderProduct.cancelOrderProduct();
        }
    }
    public void addOrderProduct(OrderProduct orderProduct){
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }
    public static Order createOrder(User user, OrderProduct... orderProducts){
        // order 생성
        long totalPrice = 0L;
        int totalQuantity = 0;

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CREATED);
        for(OrderProduct orderProduct : orderProducts){
            totalPrice += orderProduct.getOrderPrice();
            totalQuantity += orderProduct.getOrderQuantity();
            order.addOrderProduct(orderProduct);
        }
        order.setTotalPrice(totalPrice);
        order.setTotalQuantity(totalQuantity);

        return order;
    }
}
