package org.odessajavaclub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class CoffeePotSecurityConfig extends WebSecurityConfigurerAdapter {


    //the purpose of this task is to get list of user-tied objects and I do not have enough time to implement proper security,
    // so for test task purposes I will use in memory authentication with userName as unique identifier instead userId
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth
                .inMemoryAuthentication()
                .withUser("lohika_user@gmail.com")
                .password(passwordEncoder.encode("password123"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll();
    }
}
