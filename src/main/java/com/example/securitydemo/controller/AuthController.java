package com.example.securitydemo.controller;
import com.example.securitydemo.model.*; import com.example.securitydemo.repository.*; import com.example.securitydemo.security.JwtService; import com.example.securitydemo.service.*;
import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/auth")
public class AuthController {
 private final AppUserRepository users; private final PasswordEncoder encoder; private final JwtService jwt; private final RefreshTokenService refresh; private final AESService aes;
 public AuthController(AppUserRepository u,PasswordEncoder e,JwtService j,RefreshTokenService r,AESService a){users=u;encoder=e;jwt=j;refresh=r;aes=a;}
 @PostMapping("/register") public ResponseEntity<?> register(@RequestBody RegisterRequest x){
  if(x.username()==null||x.username().isBlank()||x.password()==null||x.password().isBlank())return ResponseEntity.badRequest().body(Map.of("message","Username and password are required"));
  if(users.existsByUsername(x.username()))return ResponseEntity.status(409).body(Map.of("message","Username already exists"));
  Role role=x.role()==null?Role.USER:x.role(); users.save(new AppUser(x.username(),encoder.encode(x.password()),role));
  return ResponseEntity.ok(Map.of("message","User registered successfully","username",x.username(),"role",role.name()));
 }
 @PostMapping("/login") public ResponseEntity<?> login(@RequestBody LoginRequest x){
  AppUser u=users.findByUsername(x.username()).orElse(null);
  if(u==null||!encoder.matches(x.password(),u.getPassword()))return ResponseEntity.status(401).body(Map.of("message","Invalid username or password"));
  return ResponseEntity.ok(Map.of("tokenType","Bearer","accessToken",jwt.generateToken(u.getUsername(),u.getRole().name()),"refreshToken",refresh.create(u.getUsername()).getToken(),"role",u.getRole().name()));
 }
 @PostMapping("/refresh") public ResponseEntity<?> refresh(@RequestBody RefreshRequest x){try{RefreshToken r=refresh.verify(x.refreshToken());AppUser u=users.findByUsername(r.getUsername()).orElseThrow();return ResponseEntity.ok(Map.of("tokenType","Bearer","accessToken",jwt.generateToken(u.getUsername(),u.getRole().name()),"role",u.getRole().name()));}catch(Exception e){return ResponseEntity.status(401).body(Map.of("message",e.getMessage()));}}
 @PostMapping("/logout") public ResponseEntity<?> logout(@RequestBody RefreshRequest x){refresh.revoke(x.refreshToken());return ResponseEntity.ok(Map.of("message","Logged out successfully"));}
 @PostMapping("/aes/encrypt") public ResponseEntity<?> encrypt(@RequestBody TextRequest x){try{return ResponseEntity.ok(Map.of("encrypted",aes.encrypt(x.text())));}catch(Exception e){return ResponseEntity.status(500).body(Map.of("message","Encryption failed"));}}
 @PostMapping("/aes/decrypt") public ResponseEntity<?> decrypt(@RequestBody EncryptedRequest x){try{return ResponseEntity.ok(Map.of("text",aes.decrypt(x.encrypted())));}catch(Exception e){return ResponseEntity.badRequest().body(Map.of("message","Decryption failed"));}}
 public record RegisterRequest(String username,String password,Role role){} public record LoginRequest(String username,String password){} public record RefreshRequest(String refreshToken){} public record TextRequest(String text){} public record EncryptedRequest(String encrypted){}
}