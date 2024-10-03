package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.itmentor.spring.boot_security.demo.services.UserServices;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final SuccessUserHandler successUserHandler;
    private final UserServices userServices;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, @Lazy UserServices userServices) {
        this.successUserHandler = successUserHandler;
        this.userServices = userServices;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests-> authorizeRequests
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/login","/registration").permitAll()
                        .anyRequest().hasAnyRole("USER", "ADMIN"))

                        .formLogin(formLogin->formLogin
                                .successHandler(successUserHandler)
                                .loginPage("/login")
                                .loginProcessingUrl("/process_login")
                                .failureUrl("/login?error"))

                        .logout(logout->logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout"));

        return httpSecurity.build();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userServices);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}