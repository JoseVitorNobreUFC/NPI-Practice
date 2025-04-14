package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(basic -> basic.disable())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().sameOrigin())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(requests -> requests
                        .antMatchers(HttpMethod.POST, "/login").permitAll()
                        .antMatchers("public/**/**").permitAll()
                        .antMatchers("/webjars/**").permitAll()
                        .antMatchers("/swagger-ui.html").permitAll()
                        .antMatchers("/swagger-ui/index.html").permitAll()
                        .antMatchers("/swagger-ui").permitAll()
                        .antMatchers("/v2/api-docs").permitAll()
                        .antMatchers("/v3/api-docs").permitAll()
                        .antMatchers("/swagger-resources/**").permitAll()
                        .anyRequest().authenticated())
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

}

