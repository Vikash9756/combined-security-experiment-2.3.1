package com.example.securitydemo.controller;
import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController public class SecureController {
 @GetMapping("/api/user") @PreAuthorize("hasAnyRole('USER','ADMIN')") public Map<String,String> user(){return Map.of("message","User endpoint accessed successfully","access","USER or ADMIN");}
 @GetMapping("/api/admin") @PreAuthorize("hasRole('ADMIN')") public Map<String,String> admin(){return Map.of("message","Admin endpoint accessed successfully","access","ADMIN only");}
 @GetMapping("/api/profile") @PreAuthorize("isAuthenticated()") public Map<String,String> profile(Authentication a){return Map.of("username",a.getName(),"message","Authenticated profile accessed");}
}