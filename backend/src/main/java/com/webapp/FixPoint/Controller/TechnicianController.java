package com.webapp.FixPoint.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/technician")
public class TechnicianController {

    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('ROLE_TECHNICIAN', 'ROLE_ADMIN')")
    public String sayHello() {

        return "Hello from technician endpoint!";
    }
}