package example.config;

import example.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomUserDetailsService userDetailsService) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/auth/**")
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/csrf/token").permitAll()
                        .requestMatchers("/api/items").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/warehouses").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/stocks").hasRole("ADMIN")
                        .requestMatchers("/api/movements").hasRole("ADMIN")
                        .requestMatchers("/api/suppliers").hasRole("ADMIN")
                        .requestMatchers("/api/users").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.realmName("warehouse-realm"))
                .userDetailsService(userDetailsService);

        return http.build();
    }
}