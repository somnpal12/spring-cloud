package com.sample.controller;

import com.sample.entity.User;
import com.sample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WebController {
    /*@Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userRepository.findAll());
    }*/

    @GetMapping("/test1")
    @PreAuthorize("hasAnyAuthority('READ','WRITE')")
    public String test1() {
        String val = "test1 " + UUID.randomUUID();
        System.out.println(val);
        return val;
    }

    @GetMapping("/test2")
    @PreAuthorize("hasAnyAuthority('WRITE')")
    public String test2() {
        String val = "test2 " + UUID.randomUUID();
        System.out.println(val);
        return val;
    }

    @GetMapping("/test3")
    @PreAuthorize("hasAnyAuthority('READ')")
    public String test3() {
        String val = "test3 " + UUID.randomUUID();
        System.out.println(val);
        return val;
    }
}
