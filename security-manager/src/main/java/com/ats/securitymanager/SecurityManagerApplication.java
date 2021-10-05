package com.ats.securitymanager;

import com.ats.securitymanager.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
@EntityScan(basePackages = "com.ats.user.core.models")
public class SecurityManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityManagerApplication.class, args);
	}

}
