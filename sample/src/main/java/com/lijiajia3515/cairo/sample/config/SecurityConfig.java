package com.lijiajia3515.cairo.sample.config;


import com.lijiajia3515.cairo.security.oauth2.server.resource.web.CairoBearerTokenAccessDeniedHandler;
import com.lijiajia3515.cairo.security.oauth2.server.resource.web.CairoBearerTokenAuthenticationEntryPoint;
import com.lijiajia3515.cairo.starter.service.security.oauth2.server.resource.authentication.CairoJwtAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http,
											CairoBearerTokenAuthenticationEntryPoint entryPoint,
											CairoBearerTokenAccessDeniedHandler accessDeniedHandler,
											CairoJwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
		http.csrf().disable()
			.authorizeRequests(authorizeRequests -> authorizeRequests
				.mvcMatchers("/test/**").permitAll()
				.mvcMatchers("/**").authenticated()
			)
			.oauth2ResourceServer()
			.authenticationEntryPoint(entryPoint)
			.accessDeniedHandler(accessDeniedHandler)
			.jwt()
			.jwtAuthenticationConverter(jwtAuthenticationConverter)
			.and()
			.and()
			.sessionManagement()
			.disable()
			.exceptionHandling()
			.and();
		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/webjars/**");
	}
}
