package com.gourmet.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(Authorize -> Authorize
						.requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER","ADMIN")
						.requestMatchers("/api/**").authenticated()
						.anyRequest().permitAll()
			).addFilterBefore(new JwtTokenValidator(),BasicAuthenticationFilter.class)
			.csrf(csrf->csrf.disable())
			.cors(cors->cors.configurationSource(corsConfigurationSource()));
		
		return http.build();

	}
	
	private CorsConfigurationSource corsConfigurationSource() {
		return new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration cfgConfiguration=new CorsConfiguration();
					cfgConfiguration.setAllowedOrigins(Arrays.asList(
							"http://localhost:3000"
					));
					cfgConfiguration.setAllowedMethods(Collections.singletonList("*"));
					cfgConfiguration.setAllowCredentials(true);
					cfgConfiguration.setAllowedHeaders(Collections.singletonList("*"));
					cfgConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
					cfgConfiguration.setMaxAge(3600L);
				return cfgConfiguration;
			}
			
		};
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
}
