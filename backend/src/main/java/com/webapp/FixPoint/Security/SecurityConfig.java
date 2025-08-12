package com.webapp.FixPoint.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//    @Autowired
//     JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                            .requestMatchers(HttpMethod.GET, "/public/hello").permitAll()
                            .requestMatchers(HttpMethod.GET, "/public/**").permitAll()
                            .anyRequest().authenticated();
                });
                    http.oauth2ResourceServer( t -> {
                        t.jwt(Customizer.withDefaults());
                    });
                    http.sessionManagement( t ->
                                    t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                            );

        return http.build();
    }

    @Bean
    public DefaultMethodSecurityExpressionHandler msecurity() {
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
        return defaultMethodSecurityExpressionHandler;
    }
    @Bean
    public JwtAuthenticationConverter con() {
        JwtAuthenticationConverter c = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter cv = new JwtGrantedAuthoritiesConverter();
        cv.setAuthorityPrefix("");
        cv.setAuthoritiesClaimName("roles");
        c.setJwtGrantedAuthoritiesConverter(cv);
        return c;
    }

//    private JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//
//        // Podpinamy nasz KeycloakRoleConverter i wypisujemy logi z tokena
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            KeycloakRoleConverter keycloakRoleConverter = new KeycloakRoleConverter("fixpoint-client");
//            Collection<GrantedAuthority> authorities = keycloakRoleConverter.convert(jwt);
//            System.out.println("Granted Authorities: " + authorities);
//            return authorities;
//        });
//
//        return converter;
//    }
}
