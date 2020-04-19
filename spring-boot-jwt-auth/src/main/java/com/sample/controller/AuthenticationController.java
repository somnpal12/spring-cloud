package com.sample.controller;

import org.springframework.http.HttpRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

    @GetMapping("/api/authentication")
    public String authenticate(HttpServletRequest request) {
        return  "authenticated!";
    }
}
