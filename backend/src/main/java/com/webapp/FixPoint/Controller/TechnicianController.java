package com.webapp.FixPoint.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "keycloak")
@RequestMapping("/technician")
public class TechnicianController {

    @GetMapping("/hello")
    @PreAuthorize("hasAnyRole('technician', 'admin')")
    public String sayHello() {

        return "Hello from technician endpoint!";
    }
}