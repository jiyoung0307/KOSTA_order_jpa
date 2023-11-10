package com.example.order_jpa.filter;

import com.example.order_jpa.entity.User;
import com.example.order_jpa.session.SessionConst;
import com.example.order_jpa.session.UserSession;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        UserSession userSession = (UserSession) session.getAttribute(SessionConst.SESSION_NAME);

        log.info("userSession {}", userSession);
        if (userSession == null) {      // 로그인 되지 않은 사용자는 login 하러 감(지금 요청 들어온 uri 정보를 가지고)
            String redirectURI = req.getRequestURI();
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect(req.getContextPath() + "/login?redirectURI=" + redirectURI);
            return;
        }
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
