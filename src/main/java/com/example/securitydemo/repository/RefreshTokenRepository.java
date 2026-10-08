package com.example.securitydemo.repository;
import com.example.securitydemo.model.RefreshToken; import org.springframework.data.jpa.repository.*; import java.util.Optional;
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{
 Optional<RefreshToken> findByToken(String token);
 @Modifying @Query("delete from RefreshToken r where r.username = :username") int deleteByUsername(String username);
}