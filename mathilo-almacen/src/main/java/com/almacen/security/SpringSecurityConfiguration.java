package com.almacen.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import static org.springframework.security.config.Customizer.withDefaults;
import com.almacen.service.usuarioService.UserService;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SpringSecurityConfiguration {

	@Autowired
	private UserService userService;

	@Bean
	static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
	    return (web) -> web
	      .ignoring()
	      .requestMatchers("/css/**", "/js/**", "/favicon.ico", "/images/**",
					"/fonts/**", "/scss/**", "/vendors/**");
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// enable CSRF
        http.cors(withDefaults())
        .csrf(csrf -> csrf.disable())

		.authorizeHttpRequests((authz) -> authz.requestMatchers("/registro/**").permitAll().anyRequest().authenticated())
				.formLogin((formLogin) -> formLogin.loginPage("/login").permitAll())
				.logout((logout) -> logout.logoutUrl("/logout").permitAll().invalidateHttpSession(true)
						.clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login?logout").permitAll());
		return http.build();
	}

}
