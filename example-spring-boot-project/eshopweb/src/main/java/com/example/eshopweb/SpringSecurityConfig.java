package com.example.eshopweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                // POZOR! Na poradi antMatchers zalezi!!!
                .antMatchers("/public/**")
                .permitAll()
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                .antMatchers("/**")
                .hasRole("USER")
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .logout()
                .and()
                .rememberMe();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.builder()
                        .username("jirka")
//                        .password("{noop}jirka")
                        .password("{bcrypt}$2a$10$D.qk3zAp8CmDbWD8/Rbwz.wHgVEjfvKcUo7pjqHFxILE76nZ0O3OK") // raw: "jirka"
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("jirka"));
        System.out.println(new BCryptPasswordEncoder().encode("jirka"));
        System.out.println(new BCryptPasswordEncoder().encode("jirka"));
        System.out.println(new BCryptPasswordEncoder().encode("jirka"));
        System.out.println(new BCryptPasswordEncoder().encode("jirka"));
        System.out.println(new BCryptPasswordEncoder().matches("jirka", "$2a$10$D.qk3zAp8CmDbWD8/Rbwz.wHgVEjfvKcUo7pjqHFxILE76nZ0O3OK"));
    }

}