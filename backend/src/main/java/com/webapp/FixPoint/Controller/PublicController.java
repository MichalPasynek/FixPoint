package com.webapp.FixPoint.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "keycloak")
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from public endpoint!";
    }
}