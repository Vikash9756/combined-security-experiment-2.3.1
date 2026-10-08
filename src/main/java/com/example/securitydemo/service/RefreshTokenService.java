package com.example.securitydemo.service;
import com.example.securitydemo.model.RefreshToken; import com.example.securitydemo.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.time.Instant; import java.util.UUID;
@Service public class RefreshTokenService {
 private final RefreshTokenRepository repo; private final long expirationMs;
 public RefreshTokenService(RefreshTokenRepository r,@Value("${app.refresh-token.expiration-ms}")long e){repo=r;expirationMs=e;}
 @Transactional public RefreshToken create(String username){
  repo.deleteByUsername(username);
  return repo.save(new RefreshToken(UUID.randomUUID().toString(),username,Instant.now().plusMillis(expirationMs)));
 }
 @Transactional(readOnly=true) public RefreshToken verify(String token){
  RefreshToken r=repo.findByToken(token).orElseThrow(()->new IllegalArgumentException("Invalid refresh token"));
  if(r.getExpiryDate().isBefore(Instant.now())) throw new IllegalArgumentException("Refresh token expired");
  return r;
 }
 @Transactional public void revoke(String token){repo.findByToken(token).ifPresent(repo::delete);}
}