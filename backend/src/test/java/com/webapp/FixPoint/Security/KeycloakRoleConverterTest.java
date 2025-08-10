package com.webapp.FixPoint.Security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeycloakRoleConverterTest {

    private final String clientId = "fixpoint-client";
    private final KeycloakRoleConverter converter = new KeycloakRoleConverter(clientId);

    @Test
    void shouldExtractRolesFromRealmAndClient() {
        // Poprawiony kod: Używamy Map.of() lub HashMap z jawnym typem <String, Object>
        Map<String, Object> claims = Map.of(
                "realm_access", Map.of("roles", List.of("user")),
                "resource_access", Map.of(clientId, Map.of("roles", List.of("technician")))
        );

        Jwt jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "RS256")
                .claims(c -> c.putAll(claims))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        Collection<GrantedAuthority> authorities = converter.convert(jwt);
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_TECHNICIAN")));
    }

    @Test
    void shouldReturnEmptyCollectionIfNoRolesFound() {
        // Poprawiony kod: Używamy Collections.emptyMap() z jawnym typem
        Map<String, Object> emptyClaims = Collections.emptyMap();

        Jwt jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "RS256")
                .claims(c -> c.putAll(emptyClaims))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        Collection<GrantedAuthority> authorities = converter.convert(jwt);
        assertTrue(authorities.isEmpty());
    }
}