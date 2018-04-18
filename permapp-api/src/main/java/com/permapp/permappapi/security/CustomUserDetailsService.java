package com.permapp.permappapi.security;

import static com.permapp.permappapi.security.SecurityConstants.BYCRIPT;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  private PasswordEncoder encoder = new BCryptPasswordEncoder(16);

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ApplicationUser applicationUser = loadApplicationUserByUsername(username);
    return new User(applicationUser.getUsername(),  BYCRIPT + encoder.encode(applicationUser.getPassword()),
        AuthorityUtils.createAuthorityList("ROLE_USER"));
  }

  public ApplicationUser loadApplicationUserByUsername(String usermame) {
    return new ApplicationUser("batman", "pass");
  }
}
