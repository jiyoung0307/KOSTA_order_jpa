package com.example.order_jpa.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSession {
    private long userId;
    private String name;
    private String email;
}