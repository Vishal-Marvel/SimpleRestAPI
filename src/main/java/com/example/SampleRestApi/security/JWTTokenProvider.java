package com.example.SampleRestApi.security;

import com.example.SampleRestApi.Exceptions.APIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenProvider {
     private String jwtSecret = "3F4528482B4D6251655468576D5A7134743777397A24432646294A404E635266556A586E3272357538782F4125442A472D4B6150645367566B59703373367639";

     private Long jwtExpirationDate = (long) 1000*60*30;

     public String generateToken(Authentication authentication) {
          String usernameOrEmail = authentication.getName();
          Date currentDate = new Date();
          Date expiryDate = new Date(currentDate.getTime() + jwtExpirationDate);

          String token = Jwts.builder()
                  .setSubject(usernameOrEmail)
                  .setIssuedAt(currentDate)
                  .setExpiration(expiryDate)
                  .signWith(key())
                  .compact();
          return token;
     }

     private Key key() {
          return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
     }

     //Get Username from JWT
     public String getUsername(String token) {
          Claims claims = Jwts.parserBuilder()
                  .setSigningKey(key())
                  .build()
                  .parseClaimsJws(token)
                  .getBody();
          String username = claims.getSubject();
          return username;
     }

     //validate JWT
     public boolean validateToken(String token){
          try {
               Jwts.parserBuilder()
                       .setSigningKey(key())
                       .build()
                       .parse(token);
               return true;
          }
          catch (MalformedJwtException e){
               throw new APIException("Invalid Javascript Web Token", HttpStatus.UNAUTHORIZED);
          }
          catch (ExpiredJwtException e){
               throw new APIException("Expired Javascript Web Token", HttpStatus.BAD_REQUEST);
          }
          catch (UnsupportedJwtException e){
               throw new APIException("Unsupported Javascript Web Token", HttpStatus.BAD_REQUEST);
          }
          catch (IllegalArgumentException e){
               throw new APIException("Javascript Web Token claims are empty", HttpStatus.BAD_REQUEST);
          }
          catch (SignatureException e){
               throw new APIException("Invalid JWT", HttpStatus.BAD_REQUEST);
          }
     }

}
