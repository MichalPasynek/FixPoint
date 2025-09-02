package com.webapp.FixPoint.Controller;

import com.webapp.FixPoint.Repository.TechnicianRepository;
import com.webapp.FixPoint.Technician;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@SecurityRequirement(name = "keycloak")
@RequestMapping("/technician")
public class TechnicianController {

    private final TechnicianRepository technicianRepository;

    public TechnicianController(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAnyRole('technician', 'admin')")
    public String sayHello() {

        return "Hello from technician endpoint!";
    }
    @GetMapping("/allservicants")
    @PreAuthorize("hasAnyRole('technician', 'admin')")
    public List<Technician> findAll() {
        return StreamSupport.stream(technicianRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}