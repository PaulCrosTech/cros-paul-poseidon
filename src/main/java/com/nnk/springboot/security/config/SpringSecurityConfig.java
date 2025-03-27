package com.nnk.springboot.security.config;

import com.nnk.springboot.security.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * Spring security configuration
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SpringSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructor
     *
     * @param customUserDetailsService customUserDetailsService
     */
    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        log.info("====> Loading Spring Security Configuration<====");
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * HttpSessionEventPublisher
     *
     * @return HttpSessionEventPublisher
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * Security filter for routes
     *
     * @param httpSecurity httpSecurity
     * @return SecurityFilterChain
     * @throws Exception Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("====> Loading Spring Security : SecurityFilterChain <====");
        return httpSecurity.authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/app/secure/article-details").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasRole("ADMIN")
                                .requestMatchers("/app/login", "/image/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/app/login")
                                .defaultSuccessUrl("/", true)
                                .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/app/login?errorSession")
                )
                .logout(form -> form
                        .logoutUrl("/app/logout")
                        .logoutSuccessUrl("/app/login")
                        .permitAll()
                )
                // TODO : Gestion erreur 403 - possibilité de remplacer par la méthode standard de spring security (cf. LoginController)
                .exceptionHandling(
                        exception -> exception
                                .accessDeniedPage("/app/error")
                )
                .build();
    }

    /**
     * Password encoder with BCrypt
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.info("====> Loading Spring Security : BCryptPasswordEncoder <====");
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication manager
     *
     * @param http                  http
     * @param bCryptPasswordEncoder bCryptPasswordEncoder
     * @return AuthenticationManager
     * @throws Exception Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        log.info("====> Loading Spring Security : AuthenticationManager <====");
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
}
