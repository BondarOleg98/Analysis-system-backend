package com.ats.user.query.api;

import com.ats.user.core.configurations.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AxonConfig.class})
@EntityScan(basePackages = "com.ats.user.core.models")
public class UserQueryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserQueryApiApplication.class, args);
	}

}
