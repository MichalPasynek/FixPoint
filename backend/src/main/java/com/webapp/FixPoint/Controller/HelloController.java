package com.webapp.FixPoint.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "keycloak")
public class HelloController {

    public HelloController() {}

    @GetMapping("hello")
    public String hello() {
        return "HELLLO FIXPOINT";
    }
}
