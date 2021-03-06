package com.dev.cinema.controller;

import com.dev.cinema.exceptions.AuthenticationException;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.UserRequestDto;
import com.dev.cinema.service.AuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class);
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/register")
    public User registerUser(@RequestBody UserRequestDto userDto) {
        return authenticationService.register(userDto.getEmail(), userDto.getPassword());
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody UserRequestDto userDto) {
        try {
            authenticationService.login(userDto.getEmail(), userDto.getPassword());
            return "Success";
        } catch (AuthenticationException e) {
            LOGGER.error(userDto.getEmail() + " hasn't been authenticated", e);
            return "Wrong login, password or both";
        }
    }
}
