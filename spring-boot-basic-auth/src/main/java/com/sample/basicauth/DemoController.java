package com.sample.basicauth;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DemoController {

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
