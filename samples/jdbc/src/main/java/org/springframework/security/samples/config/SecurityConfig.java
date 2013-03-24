package org.springframework.security.samples.config;


import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.AuthenticationBuilder;
import org.springframework.security.config.annotation.web.EnableWebSecurity;
import org.springframework.security.config.annotation.web.ExpressionUrlAuthorizations;
import org.springframework.security.config.annotation.web.HttpConfiguration;
import org.springframework.security.config.annotation.web.SpringSecurityFilterChainBuilder.IgnoredRequestRegistry;
import org.springframework.security.config.annotation.web.WebSecurityConfigurerAdapater;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapater {
    @Autowired
    private DataSource dataSource;

    protected void ignoredRequests(IgnoredRequestRegistry ignoredRequests) {
        ignoredRequests
            .antMatchers("/resources/**");
    }

    protected AuthenticationManager authenticationManager(
            AuthenticationBuilder authenticationRegistry) throws Exception {
        return authenticationRegistry
            .jdbcUserDetailsManager(dataSource)
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("USER", "ADMIN").and()
                .withDefaultSchema()
                .and()
            .build();
    }

    @Bean public Object now() {
        System.out.println(dataSource);
        return new Date();
    }

    protected void authorizeUrls(
            ExpressionUrlAuthorizations interceptUrls) {
        interceptUrls
            .antMatchers("/users**","/sessions/**").hasRole("ADMIN")
            .antMatchers("/resources/**","/signup").permitAll()
            .antMatchers("/**").hasRole("USER");
    }

    protected void configure(HttpConfiguration http) throws Exception {
        http
            .formLogin()
            .permitAll();
    }
}
