package com.webapp.FixPoint.Security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final String clientId;

    public KeycloakRoleConverter(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // 🔹 Log: wszystkie claims w tokenie
        System.out.println("===== JWT Claims =====");
        jwt.getClaims().forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
        System.out.println("======================");

        Collection<GrantedAuthority> realmAuthorities = extractRealmRoles(jwt);
        Collection<GrantedAuthority> clientAuthorities = extractClientRoles(jwt);

        // 🔹 Log: wszystkie zebrane role w Spring Security
        Collection<GrantedAuthority> allAuthorities = Stream.concat(realmAuthorities.stream(), clientAuthorities.stream())
                .collect(Collectors.toSet());
        System.out.println("Final Granted Authorities: " + allAuthorities);

        return allAuthorities;
    }

    /**
     * Pobiera role przypisane w Realm Access (globalne role w Keycloak).
     */
    private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null || !(realmAccess.get("roles") instanceof Collection)) {
            System.out.println("Brak realm_access lub brak listy ról");
            return List.of();
        }

        List<String> roles = new ArrayList<>((Collection<String>) realmAccess.get("roles"));
        System.out.println("Realm roles: " + roles);

        return roles.stream()
                .filter(Objects::nonNull)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }

    /**
     * Pobiera role przypisane do konkretnego klienta (aplikacji).
     */
    private Collection<GrantedAuthority> extractClientRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) {
            System.out.println("Brak resource_access w tokenie");
            return List.of();
        }

        Object client = resourceAccess.get(clientId);
        if (!(client instanceof Map) || !(((Map<?, ?>) client).get("roles") instanceof Collection)) {
            System.out.println("Brak wpisu dla klienta " + clientId + " lub brak listy ról");
            return List.of();
        }

        List<String> roles = new ArrayList<>((Collection<String>) ((Map<?, ?>) client).get("roles"));
        System.out.println("Client roles: " + roles);

        return roles.stream()
                .filter(Objects::nonNull)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }
}
