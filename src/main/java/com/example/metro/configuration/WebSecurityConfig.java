package com.example.metro.configuration;

import com.example.metro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/stations/**").authenticated()
                .antMatchers("/registration").anonymous()
                .anyRequest().authenticated();
        http
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/station/search", true)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Configuration
    @RequiredArgsConstructor
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
        private final UserService userService;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService).passwordEncoder(userService.getEncoder());
        }
    }

}
