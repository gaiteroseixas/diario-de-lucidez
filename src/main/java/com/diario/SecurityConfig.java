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
            	    .loginPage("/login") 
            	    .loginProcessingUrl("/login") // O Spring Security "ouve" o POST aqui
            	    .usernameParameter("email")    // Nome exato do campo no seu HTML
            	    .passwordParameter("senha")    // Nome exato do campo no seu HTML
            	    .defaultSuccessUrl("/home", true) // O "true" força ele a ir para a home
            	    .failureUrl("/login?error=true")  // Se errar a senha, avisa na URL
            	    .permitAll()
            	)
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}