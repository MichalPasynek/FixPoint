package com.webapp.FixPoint.Security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpointShouldBePermitted() throws Exception {
        mockMvc.perform(get("/public/hello"))
                .andExpect(status().isOk());
    }

    @Test
    void adminEndpointShouldBeForbiddenForTechnician() throws Exception {
        mockMvc.perform(get("/admin/hello")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_TECHNICIAN"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminEndpointShouldBePermittedForAdmin() throws Exception {
        mockMvc.perform(get("/admin/hello")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk());
    }

    @Test
    void technicianEndpointShouldBePermittedForTechnician() throws Exception {
        mockMvc.perform(get("/technician/hello")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_TECHNICIAN"))))
                .andExpect(status().isOk());
    }

    @Test
    void anyRequestShouldBeUnauthorizedWithoutToken() throws Exception {
        mockMvc.perform(get("/secure/hello"))
                .andExpect(status().isUnauthorized());
    }
}