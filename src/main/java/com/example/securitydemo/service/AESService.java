package com.example.securitydemo.service;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import javax.crypto.Cipher; import javax.crypto.spec.SecretKeySpec; import java.nio.charset.StandardCharsets; import java.util.Base64;
@Service public class AESService {
 private final SecretKeySpec key; public AESService(@Value("${app.aes.secret}")String s){key=new SecretKeySpec(s.getBytes(StandardCharsets.UTF_8),"AES");}
 public String encrypt(String text)throws Exception{Cipher c=Cipher.getInstance("AES/ECB/PKCS5Padding");c.init(Cipher.ENCRYPT_MODE,key);return Base64.getEncoder().encodeToString(c.doFinal(text.getBytes(StandardCharsets.UTF_8)));}
 public String decrypt(String enc)throws Exception{Cipher c=Cipher.getInstance("AES/ECB/PKCS5Padding");c.init(Cipher.DECRYPT_MODE,key);return new String(c.doFinal(Base64.getDecoder().decode(enc)),StandardCharsets.UTF_8);}
}