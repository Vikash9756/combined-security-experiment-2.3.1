package com.example.securitydemo.security;
import io.jsonwebtoken.Claims; import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException; import java.util.List;
@Component public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private final JwtService jwtService; public JwtAuthenticationFilter(JwtService j){jwtService=j;}
 protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization");
  if(h!=null&&h.startsWith("Bearer ")){try{
   Claims c=jwtService.parseToken(h.substring(7)); String u=c.getSubject(); String r=c.get("role",String.class);
   if(u!=null&&r!=null) SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(u,null,List.of(new SimpleGrantedAuthority("ROLE_"+r))));
  }catch(Exception e){SecurityContextHolder.clearContext();}}
  chain.doFilter(req,res);
 }
}