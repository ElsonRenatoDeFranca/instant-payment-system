package com.example.userservice.application;

import com.example.userservice.domain.User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    public User getUser(String id) {
        User user = new User();
        user.setId(id);
        user.setName("Test User");
        return user;
    }
}
