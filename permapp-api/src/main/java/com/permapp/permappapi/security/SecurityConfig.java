package com.permapp.permappapi.security;

import com.permapp.permappapi.security.jwt.JWTAuthenticationFilter;
import com.permapp.permappapi.security.jwt.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private final CustomUserDetailsService customUserDetailService;

  @Autowired
  public SecurityConfig(CustomUserDetailsService customUserDetailService) {
    this.customUserDetailService = customUserDetailService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
      .antMatchers(HttpMethod.POST, "/cadastro").permitAll()
      .anyRequest().authenticated()
      .antMatchers("*/floor1/*").hasRole("USER")
      .antMatchers("*/floor2/*").hasRole("ADMIN")
    .and()
    .addFilter(new JWTAuthenticationFilter(authenticationManager()))
    .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailService));
  }
}
