package com.webapp.FixPoint.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "keycloak")
public class AdminController {

    @GetMapping("/hello")
    @PreAuthorize("hasRole('admin')")
    public String sayHello() {
        return "Hello from admin endpoint!";
    }
}