package com.example.securitydemo.security;
import com.example.securitydemo.model.AppUser; import com.example.securitydemo.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.userdetails.*; import org.springframework.stereotype.Service; import java.util.List;
@Service public class CustomUserDetailsService implements UserDetailsService {
 private final AppUserRepository repo; public CustomUserDetailsService(AppUserRepository r){repo=r;}
 public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
  AppUser u=repo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
  return User.withUsername(u.getUsername()).password(u.getPassword()).authorities(List.of(new SimpleGrantedAuthority("ROLE_"+u.getRole().name()))).build();
 }
}