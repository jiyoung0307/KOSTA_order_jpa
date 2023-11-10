package com.example.order_jpa.controller;

import com.example.order_jpa.dto.UserLoginDTO;
import com.example.order_jpa.entity.User;
import com.example.order_jpa.service.LoginService;
import com.example.order_jpa.session.SessionConst;
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
@RequestMapping
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String login(){
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDTO userLoginDTO,
                        HttpServletResponse response,
                        HttpServletRequest request){
        User loginUser = loginService.login(userLoginDTO);
        if(loginUser == null){
            return "redirect:/login";
        }
        // 로그인에 성공시 세션 생성
        UserSession userSession = new UserSession();
        userSession.setUserId(loginUser.getUserId());
        userSession.setName(loginUser.getName());
        userSession.setEmail(loginUser.getEmail());
        log.info("UserSession ==> " + userSession);

        HttpSession session = request.getSession(true);
//        String uuid = UUID.randomUUID().toString();
        session.setAttribute(SessionConst.SESSION_NAME, userSession);

        // 로그인에 성공시 session의 id를 쿠키 설정
        Cookie cookie = new Cookie(SessionConst.COOKIE_NAME, SessionConst.SESSION_NAME);
        cookie.setPath("/");
        cookie.setMaxAge(600);
        response.addCookie(cookie);

        return "redirect:/order/add";
    }

    @GetMapping("/logout")  /** request - 쿠키 response - 세션 */
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(SessionConst.COOKIE_NAME, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
