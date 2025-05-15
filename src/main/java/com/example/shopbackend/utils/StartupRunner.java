package com.example.shopbackend.utils;

import com.example.shopbackend.entity.User;
import com.example.shopbackend.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {
    private final UserService userService;

    public StartupRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.initializeDefaultAdmin();
    }
}
