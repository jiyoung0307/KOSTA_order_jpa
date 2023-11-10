package com.example.order_jpa.controller;

import com.example.order_jpa.dto.OrderDTO;
import com.example.order_jpa.entity.Order;
import com.example.order_jpa.entity.OrderProduct;
import com.example.order_jpa.exception.NoEnoughStockException;
import com.example.order_jpa.service.OrderService;
import com.example.order_jpa.service.ProductService;
import com.example.order_jpa.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/list")
    public String getAllOrders(Model model) {
        List<Order> allOrders = orderService.getAllOrders();
        model.addAttribute("orders", allOrders);
        return "order/orderList";
    }

    @GetMapping("/list/{userId}")
    public String getAllOrdersByUserId(@PathVariable Long userId,
                                       Model model) {
        List<Order> allOrdersByUserId = orderService.getAllOrdersByUserId(userId);
        model.addAttribute("orders", allOrdersByUserId);
        return "order/orderList";
    }

    @GetMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/order/list";
    }

    @GetMapping("/info/{orderId}")
    public String getOrderInfo(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderInfo(orderId);
        model.addAttribute("order", order);
        return "order/orderInfo";
    }

    @GetMapping("/add")
    public String addOrder(Model model,
                           HttpServletRequest request) {
        /** 로그인한 사용자의 정보를 쿠키로부터 얻어오기 */
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            log.info("cookie.name ==> ", cookie.getName());
//            System.out.println(cookie.getName());
            if(cookie.getName().equals("uerId")) {
                Long userId = Long.parseLong(cookie.getValue());

            }
        }

        /** 사용자의 정보를 model에 넘겨주기 */
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("products", productService.getAllProducts());
        return "order/orderForm";
    }


    @PostMapping("/add")
    public String addOrder(@ModelAttribute OrderDTO orderDTO) throws NoEnoughStockException {
        orderService.addOrder(orderDTO);
        return "redirect:/order/list";
    }
}
