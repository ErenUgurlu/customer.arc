package com.customer.arc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {


	
    @Autowired
    private AuthTokenFilter authTokenFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		try {
			http
			.csrf().disable()
			.cors().disable()
			.authorizeHttpRequests((authorizeHttpRequests) ->
				authorizeHttpRequests
					.requestMatchers(
							"api/auth/**","/js/**","/css/**","/img/**","/fonts/**").permitAll()
					.anyRequest().authenticated()
			)
			.sessionManagement((sessionManagement) ->
			sessionManagement
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
	        //.formLogin((formLogin) -> formLogin.disable())
	        //.httpBasic((httpBasic)-> httpBasic.disable())
			.addFilterBefore( authTokenFilter, UsernamePasswordAuthenticationFilter.class);
			
			} catch (Exception e) {
				log.error("securityFilterChain de hata yaşandı. Hata: "+e.getMessage());
			}
		return http.build();
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	

}
