package com.example.order_jpa.service;

import com.example.order_jpa.dto.UserLoginDTO;
import com.example.order_jpa.entity.User;
import com.example.order_jpa.repository.JPAUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final JPAUserRepository userRepository;

    public User login(UserLoginDTO userLoginDTO) {
        Optional<User> byEmail = userRepository.findByEmail(userLoginDTO.getEmail());
        if(!byEmail.isEmpty()) {
            User user = byEmail.get();
            if(user.getPassword().equals(userLoginDTO.getPassword())) {
                return user;
            }
        }
        return null;
    }
}