package com.example.securitydemo.config;
import com.example.securitydemo.security.JwtAuthenticationFilter; import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.*; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration @EnableMethodSecurity
public class SecurityConfig {
 private final JwtAuthenticationFilter filter; public SecurityConfig(JwtAuthenticationFilter f){filter=f;}
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
  return http.csrf(c->c.disable()).headers(h->h.frameOptions(f->f.sameOrigin())).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/auth/**","/h2-console/**").permitAll().anyRequest().authenticated())
   .addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class).build();
 }
}