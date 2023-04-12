package com.zagvladimir.security;

import com.zagvladimir.security.jwt.AuthEntryPointJwt;
import com.zagvladimir.security.jwt.AuthTokenFilter;
import com.zagvladimir.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
  private static final String ADMIN = "ADMIN";
  private static final String USER = "USER";
  private static final String MODERATOR = "MODERATOR";

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  @Primary
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        .antMatchers("/swagger-ui/**").permitAll()
        .antMatchers("/v3/api-docs/**").permitAll()

        .antMatchers("/api/auth/**").permitAll()

        .antMatchers("/api/admin/**").hasAnyRole(ADMIN)

        .antMatchers(HttpMethod.GET,"/api/item-categories/**").permitAll()
        .antMatchers(HttpMethod.POST,"/api/item-categories/**").hasAnyRole(ADMIN)
        .antMatchers(HttpMethod.DELETE,"/api/item-categories/**").hasAnyRole(ADMIN, MODERATOR)

        .antMatchers(HttpMethod.GET,"/api/grades/**").permitAll()
        .antMatchers(HttpMethod.POST,"/api/grades/**").hasAnyRole(ADMIN,USER)
        .antMatchers(HttpMethod.PATCH,"/api/grades/**").hasAnyRole(ADMIN,USER, MODERATOR)

        .antMatchers(HttpMethod.GET,"/api/items/**").permitAll()
        .antMatchers(HttpMethod.POST,"/api/items/**").hasAnyRole(ADMIN,USER,MODERATOR)

        .antMatchers(HttpMethod.GET,"/api/items-leased/**").permitAll()
        .antMatchers(HttpMethod.POST,"/api/items-leased/**").hasAnyRole(ADMIN,USER,MODERATOR)

        .antMatchers(HttpMethod.GET,"/api/roles/**").hasRole(ADMIN)
        .antMatchers(HttpMethod.POST,"/api/roles/**").hasRole(ADMIN)

        .antMatchers(HttpMethod.GET,"/api/roles/**").permitAll()
        .antMatchers(HttpMethod.POST,"/api/roles/**").hasAnyRole(ADMIN,MODERATOR)

        .antMatchers(HttpMethod.GET,"/api/users/**").hasAnyRole(USER,ADMIN,MODERATOR)
        .antMatchers(HttpMethod.POST,"/api/users/**").hasAnyRole(USER,ADMIN,MODERATOR)
        .antMatchers(HttpMethod.PUT,"/api/users/**").hasAnyRole(USER,ADMIN,MODERATOR)
        .antMatchers(HttpMethod.PATCH,"/api/users/**").hasAnyRole(USER,ADMIN,MODERATOR)

        .anyRequest().authenticated();
    
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }
}
