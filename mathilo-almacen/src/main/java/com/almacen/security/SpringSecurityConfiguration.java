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

    // Bean para la creación de un codificador de contraseñas
    @Bean
    static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración global para la autenticación
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    // Customizador para ignorar ciertas rutas
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
          .ignoring()
          .requestMatchers("/css/**", "/js/**", "/favicon.ico", "/images/**",
                    "/fonts/**", "/scss/**", "/vendors/**");
    }

    // Configuración de las reglas de seguridad HTTP
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configuración para habilitar CSRF
        http.cors(withDefaults())
            .csrf(csrf -> csrf.disable())

            // Reglas de autorización HTTP
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/registro/**").permitAll() // Permite acceso sin autenticación a /registro/**
                .anyRequest().authenticated()) // Requiere autenticación para cualquier otra solicitud

            // Configuración del formulario de inicio de sesión y cierre de sesión
            .formLogin((formLogin) -> formLogin
                .loginPage("/login").permitAll()) // Página de inicio de sesión permitida para todos
            .logout((logout) -> logout
                .logoutUrl("/logout").permitAll() // URL de cierre de sesión permitida para todos
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").permitAll()); // Página de cierre de sesión permitida para todos

        return http.build();
    }
}
