package com.photographer.photoalbum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


    /* per definire un AuthenticationProvider ho bisogno di:
    - uno UserDetailsService
    - un PasswordEncoder
   */
    //UserDetailsService
    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    //PasswordEncoder
    @Bean
    PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder cripta una password
        // BCryptPasswordEncoder EncodeObj = new BCryptPasswordEncoder();
        //System.out.println(EncodeObj.encode("password123"));
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        // creo l'authenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //setto il PasswordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        //setto lo UserDetailsService
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // definisco la catena di filtri
        http.authorizeHttpRequests()
                .requestMatchers("/categories").hasAuthority("ADMIN")
                .requestMatchers("/photos/edit/**").hasAuthority("ADMIN")
                .requestMatchers("/photos/create").hasAuthority("ADMIN")
                .requestMatchers("/api/**").permitAll()
                .requestMatchers(HttpMethod.POST).hasAuthority("ADMIN")
                .requestMatchers("/photos/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();
        http.csrf().disable();
        return http.build();
    }
}
