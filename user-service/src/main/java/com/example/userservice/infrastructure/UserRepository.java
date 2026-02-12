package com.example.userservice.infrastructure;

import com.example.userservice.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    // Example stub
    public User findById(String id) {
        User user = new User();
        user.setId(id);
        user.setName("Test User");
        return user;
    }
}

