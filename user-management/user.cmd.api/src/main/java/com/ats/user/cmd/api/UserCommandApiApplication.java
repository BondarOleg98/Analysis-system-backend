package com.ats.user.cmd.api;

import com.ats.user.core.configurations.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@Import({AxonConfig.class})
public class UserCommandApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCommandApiApplication.class, args);
	}

}
