package com.example.securitydemo.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="refresh_tokens")
public class RefreshToken {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(unique=true,nullable=false) private String token;
 @Column(nullable=false) private String username;
 @Column(nullable=false) private Instant expiryDate;
 public RefreshToken(){}
 public RefreshToken(String t,String u,Instant e){token=t;username=u;expiryDate=e;}
 public Long getId(){return id;} public String getToken(){return token;} public String getUsername(){return username;} public Instant getExpiryDate(){return expiryDate;}
}