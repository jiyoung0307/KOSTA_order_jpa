package com.example.order_jpa;

import com.example.order_jpa.filter.LogFilter;
import com.example.order_jpa.filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        FilterRegistrationBean<LogFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);// 1번
        filterRegistrationBean.addUrlPatterns("/*");// 전부 다 적용해
        return filterRegistrationBean;
    }

    @Bean
    FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setOrder(2);     // 2번
        filterRegistrationBean.addUrlPatterns("/order/*", "/product/add/*");    // order 요청에 대해서만 적용해
        return filterRegistrationBean;
    }
}
