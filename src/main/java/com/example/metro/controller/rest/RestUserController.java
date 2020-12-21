package com.example.metro.controller.rest;

import com.example.metro.dto.user.UserRegistrationWrapper;
import com.example.metro.dto.user.UserWrapper;
import com.example.metro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class RestUserController {
    private final UserService service;

    @PostMapping("registration")
    public UserWrapper registration(@RequestBody UserRegistrationWrapper wrapper) {
        return service.registration(wrapper);
    }
}
