package com.permapp.permappapi.security;

public class SecurityConstants {
  public static final String SECRET        = "secre";
  public static final String TOKEN_PREFIX  = "Bearer";
  public static final String HEADER_STRING = "Authorization";
  public static final long EXPIRATION_TIME = 864_000_000L; //1 dia

  public static final String BYCRIPT       = "{bcrypt}";

  //roles
  public static final String ADMIN         = "ADMIN";
  public static final String DEFAULT_USER  = "DEFAULT_USER";
  public static final String SPECIAL_USER  = "SPECIAL_USER";
}
