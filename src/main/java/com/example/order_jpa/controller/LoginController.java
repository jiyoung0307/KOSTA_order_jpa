package com.example.order_jpa.controller;

import com.example.order_jpa.dto.UserLoginDTO;
import com.example.order_jpa.entity.User;
import com.example.order_jpa.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    @GetMapping
    public String login() {
        return "login/loginForm";
    }

    @PostMapping
    public String login(@ModelAttribute UserLoginDTO userLoginDTO) {
        User loginUser = loginService.login(userLoginDTO);
        if (loginUser == null) {
            return "redirect:/login";
        }
        return "redirect:/order/add";

    }
}
