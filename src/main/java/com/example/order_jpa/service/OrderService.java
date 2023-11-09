package com.example.order_jpa.service;

import com.example.order_jpa.entity.Order;
import com.example.order_jpa.repository.JPAOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final JPAOrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getAllOrdersByUserId(Long userId) {    /** 리팩토링 */
        return orderRepository.findOrdersByUserId(userId);
    }

    public void addOrder(Order order) {
        orderRepository.save(order);    /** 리팩토링 */
    }

    public void cancelOrder(Order order) {
        orderRepository.delete(order);    /** 리팩토링 */
    }

    public Order getOrderInfo(Long orderId) {    /** 리팩토링 */
        return orderRepository.findById(orderId).get();
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);    /** 리팩토링 */
    }
}
