package com.permapp.permappapi.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.permapp.permappapi.jwt.service.TokenAuthenticationService;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

  public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
    super(new AntPathRequestMatcher(url));
    setAuthenticationManager(authenticationManager);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException, IOException, ServletException {
    UserCredentials cred = new ObjectMapper().readValue(req.getInputStream(), UserCredentials.class);
    return getAuthenticationManager().authenticate(
      new UsernamePasswordAuthenticationToken(
          cred.getUsername(), cred.getPassword(),Collections.<GrantedAuthority>emptyList()
      )
    );
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    TokenAuthenticationService.addAuthentication(response, authResult.getName());
  }
}
