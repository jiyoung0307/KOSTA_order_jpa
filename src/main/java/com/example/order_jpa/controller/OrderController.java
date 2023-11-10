package com.example.order_jpa.controller;

import com.example.order_jpa.dto.OrderDTO;
import com.example.order_jpa.entity.Order;
import com.example.order_jpa.entity.User;
import com.example.order_jpa.exception.NoEnoughStockException;
import com.example.order_jpa.service.OrderService;
import com.example.order_jpa.service.ProductService;
import com.example.order_jpa.service.UserService;
import com.example.order_jpa.session.SessionConst;
import com.example.order_jpa.session.UserSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
        // 로그인한 사용자의 정보를 세션으로부터 얻어오기
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            log.info("cookie.name ==> " + cookie.getName());
            if (cookie.getName().equals(SessionConst.COOKIE_NAME)) {
//                로그인 이외의 공간에서는 모두 false
                HttpSession session = request.getSession(false);
                UserSession userSession = (UserSession) session.getAttribute(cookie.getValue());
                log.info("UserSession ==> " + userSession);

                Long userId = userSession.getUserId();
                User user = userService.getUserById(userId);
                model.addAttribute("user", user);

                // 사용자의 정보를 model 에 넘겨주기
                model.addAttribute("users", userService.getAllUsers());
                model.addAttribute("products", productService.getAllProducts());
                return "order/orderForm";
            }
            //System.out.println("cookie.name ==> " + cookie.getName());
        }
        return "redirect:/login";
    }


    @PostMapping("/add")
    public String addOrder(@ModelAttribute OrderDTO orderDTO) throws NoEnoughStockException {
        orderService.addOrder(orderDTO);
        return "redirect:/order/list";
    }
}