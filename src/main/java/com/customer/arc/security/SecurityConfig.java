package com.customer.arc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.customer.arc.services.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


	
    @Autowired
    private AuthTokenFilter authTokenFilter;
    
    @Autowired
    private UserDetailsServiceImpl detailsServiceImpl;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		try {
			http
			.csrf((csrf) -> csrf.ignoringRequestMatchers("/callback","/errorPageNotFound","/errorPage","/errorPagePayment","/robots.txt","/actuator/**")
					.csrfTokenRepository(new CookieCsrfTokenRepository())   
			)
			.authorizeHttpRequests((authorizeHttpRequests) ->
				authorizeHttpRequests
					.requestMatchers(
							"/js/**","/css/**","/img/**","/fonts/**").permitAll()
					.requestMatchers("/testAdmin","/yonetim").hasRole("ADMIN")
					.requestMatchers("/testAdmin","/yonetim").hasAuthority("ADMIN")
					
					.anyRequest().authenticated()
			)
			.exceptionHandling((exceptionHandling) ->
				exceptionHandling
					.accessDeniedPage("/errorPageNotFound")
					
			)
			.sessionManagement((sessionManagement) ->
			sessionManagement
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			)
	        .formLogin((formLogin) -> formLogin.disable())
	        .httpBasic((httpBasic)->httpBasic.disable())
			.logout((logout) ->
				logout.deleteCookies("JWT","JSESSIONID")
					.invalidateHttpSession(true)
					.logoutUrl("/logout")
					.logoutSuccessUrl("https://www.levleb.com")
			)
			.authenticationProvider(authenticationProvider())
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
	public AuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(detailsServiceImpl);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	

}
