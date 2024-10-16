package org.klozevitz.phat_store_mvc_java_311.configs;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.model.secuirty.UserDetailsServiceImplementation;
import org.klozevitz.phat_store_mvc_java_311.repositories.ApplicationUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DataSource dataSource;
    private final ApplicationUserRepository applicationUserRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/login").anonymous()
                                .requestMatchers("/item/addToCart").authenticated()
                                .anyRequest().permitAll()
                )
//                .formLogin(form ->
//                        form
////                                .loginPage("/login")
//                                .defaultSuccessUrl("/")
//                                .failureUrl("/login"))
                .httpBasic();

//                .logout(logout -> logout
//                                .logoutUrl("/logout")
//                                .logoutSuccessUrl("/login"));
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImplementation(applicationUserRepository);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public UserDetailsManager userDetailsManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authManager = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .build();

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setAuthenticationManager(authManager);
        return jdbcUserDetailsManager;
    }
}
