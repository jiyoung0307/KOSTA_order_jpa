package com.example.order_jpa.controller;

import com.example.order_jpa.dto.UserLoginDTO;
import com.example.order_jpa.entity.User;
import com.example.order_jpa.service.LoginService;
import com.example.order_jpa.session.UserSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @GetMapping
    public String login() {
        return "login/loginForm";
    }

    @PostMapping
    public String login(@ModelAttribute UserLoginDTO userLoginDTO,
                        HttpServletResponse response,
                        HttpServletRequest request) {
        User loginUser = loginService.login(userLoginDTO);
        if (loginUser == null) {
            return "redirect:/login";
        }
        /** 로그인 성공시 세션 생성 */
        UserSession userSession = new UserSession();
        userSession.setUserId(loginUser.getUserId());
        userSession.setName(loginUser.getName());
        userSession.setEmail(loginUser.getEmail());
        log.info("UserSession ==> " + userSession);

        HttpSession session = request.getSession(true);
        String uuid = UUID.randomUUID().toString();
        session.setAttribute(uuid, userSession);

        /** 로그인에 성공시 session의 id를쿠키 설정 */
        Cookie cookie = new Cookie("userId", uuid);
        cookie.setPath("/");
        cookie.setMaxAge(600);
        response.addCookie(cookie);

        return "redirect:/order/add";
    }
}
