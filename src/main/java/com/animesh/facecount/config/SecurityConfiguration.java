package com.animesh.facecount.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Defines the configurations for securing the API
 *
 * @version 1.0
 */
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
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
                .cors(Customizer.withDefaults())
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/login", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs").permitAll()
                        //system
                        .requestMatchers("api/system/**").hasAnyRole("SYSTEM", "ADMIN")
                        //attendance
                        /*.requestMatchers(HttpMethod.GET, "api/attendance/**").hasAnyRole("ADMIN", "FACULTY", "MODERATOR", "SYSTEM")
                        .requestMatchers(HttpMethod.POST, "api/attendance/**").hasAnyRole("ADMIN", "MODERATOR", "SYSTEM")
                        .requestMatchers("api/attendance/fetch/")*/
                        //faculty
                        .requestMatchers("/api/faculty/me").hasAnyRole("FACULTY", "MODERATOR", "ADMIN")
                        .requestMatchers("/api/faculty/admin/details/{facultyId}").hasAnyRole("MODERATOR", "ADMIN")
                        .requestMatchers("/api/faculty/admin/dept/{dept}").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/api/faculty/admin/all").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/api/faculty/admin/update/{facultyId}").hasRole("ADMIN")
                        .requestMatchers("/api/faculty/admin/register").hasRole("ADMIN")
                        .requestMatchers("/api/faculty/admin/delete/{facultyId}").hasRole("ADMIN")
                        //student
                        .requestMatchers(HttpMethod.GET, "/api/student/me").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/student/admin/**").hasAnyRole("FACULTY", "MODERATOR", "ADMIN")
                        .requestMatchers("/api/student/admin/update/{student_id}").hasAnyRole("MODERATOR", "ADMIN")
                        .requestMatchers("/api/student/admin/register").hasAnyRole("MODERATOR", "ADMIN")
                        .requestMatchers("/api/student/admin/delete/{student_id}").hasAnyRole("MODERATOR", "ADMIN")

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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") // React app URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
