package com.wpfinki.airlinesproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public AuthenticationManager customAuthenticaionManager() throws Exception{
        return authenticationManager();
    }
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
    httpSecurity.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/showReg", "/registerUser", "/login", "/showLogin", "/login/*","*").permitAll()
            .antMatchers("/admin/showAddFlight","/admin/addFlight","/admin/*").hasAuthority("ADMIN").anyRequest().authenticated()
            .and().csrf().disable();
    }
}
