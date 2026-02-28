package com.diario;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita para facilitar o desenvolvimento inicial
            .authorizeHttpRequests(auth -> auth
                // Adicionamos "/" e "/index" para garantir que a página inicial seja pública
                .requestMatchers("/", "/index", "/cadastro", "/login", "/css/**", "/js/**", "/images/**").permitAll() 
                .anyRequest().authenticated() // Todo o resto exige login
            )
            .formLogin(form -> form
                .loginPage("/login") // Sua página de login customizada
                .defaultSuccessUrl("/home", true) // Para onde vai depois de logar
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}