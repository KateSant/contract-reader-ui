package com.thinktalkbuild.contractreader.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@SpringBootApplication
public class ContractReaderUIApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ContractReaderUIApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(a -> a
						.antMatchers(
								"/",
								"/error",
								"/webjars/**").permitAll()
						.anyRequest().authenticated()
				)
//				.exceptionHandling(e -> e
//						.authenticationEntryPoint(
//								new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//				)
				.oauth2Login();
		// @formatter:on
	}

}
