package com.webapp.FixPoint;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
		name = "keycloak"
		,openIdConnectUrl = "http://localhost:8081/realms/fix-point/.well-known/openid-configuration"
		,scheme = "bearer"
		,type = SecuritySchemeType.OPENIDCONNECT
		,in = SecuritySchemeIn.HEADER
)
public class FixPointApplication {

	public static void main(String[] args) {
		SpringApplication.run(FixPointApplication.class, args);

	}

}