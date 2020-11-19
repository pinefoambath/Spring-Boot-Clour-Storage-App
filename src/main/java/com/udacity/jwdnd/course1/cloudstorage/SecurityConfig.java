package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthenticationService authenticationService;
    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    // here we are telling Spring to use THIS authenticationservice
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }
    // real meat of spring security - what do we need log ins for and what is access level?
    @Override
    // every method we take will be a method call on a HttpSecurity object
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // webpages to access without authorisation
                .antMatchers("/signup", "/postLogin", "/css/**", "/js/**").permitAll()
//               and the next line says we need permission / authentication for all other requests
//               .anyRequest().authenticated();
                .antMatchers("/", "/home*", "/result*").authenticated();
        // here spring auto-generates a log in page, though we can add out own css:
        http.formLogin()
                .loginPage("/login")
                // we want the login to be accessible by everyone:
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
        http.formLogin()
                .defaultSuccessUrl("/home", true);
    }
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

