package com.example.securitydemo.model;
import jakarta.persistence.*;
@Entity @Table(name="app_users")
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(unique=true,nullable=false) private String username;
 @Column(nullable=false) private String password;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;
 public AppUser(){}
 public AppUser(String u,String p,Role r){username=u;password=p;role=r;}
 public Long getId(){return id;} public String getUsername(){return username;} public String getPassword(){return password;} public Role getRole(){return role;}
}