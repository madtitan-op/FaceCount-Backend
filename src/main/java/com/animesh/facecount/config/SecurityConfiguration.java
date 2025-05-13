package com.animesh.facecount.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Defines the configurations for securing the API
 * 
 * @author Animesh Mahata
 * @version 1.0
 */
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    /**
     * Will be used to authenticate the user
     */
    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider(new BCryptPasswordEncoder(12));
        auth.setUserDetailsService(userDetailsService);

        return auth;
    }

    /**
     * <p> -> Disables csrf <br>
     * -> Defines which URLs should be authenticated <br>
     * -> Keep the httpBasic for sign-in <br>
     * -> Makes the session stateless <br>
     * -> Adds the JwtFilter before the UsernamePasswordAuthenticationFilter</p>
     * @param http gets the instance of HttpSecurity to customize it
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("api/auth/login", "swagger-ui.html", "swagger-ui/**", "/v3/api-docs").permitAll()
                        .requestMatchers("api/attendance/admin/**", "api/users/admin/**").hasRole("ADMIN")
                        .requestMatchers("api/attendance/fetch/**", "api/users/student/**").hasAnyRole("FACULTY", "STUDENT", "ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Returns AuthenticationManager interface for Dependency Injection
     * @param configuration AuthenticationConfiguration class for getting AuthenticationManager class to perform DEPENDENCY INJECTION
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authMgr(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

}
