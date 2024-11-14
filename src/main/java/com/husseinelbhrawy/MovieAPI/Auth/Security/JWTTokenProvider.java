package com.husseinelbhrawy.MovieAPI.Auth.Security;

import com.husseinelbhrawy.MovieAPI.Auth.Entity.User;
import com.husseinelbhrawy.MovieAPI.Exceptions.MovieAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenProvider {
    @Value("${app.jwt-secret}")
    private  String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private  long jwtExpirationDate;

    //! Generate JWT Token
    public  String generateToken(User user) {

        String username = user.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);


        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();

    }


    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //! Get Username from JWT Token
    public String getUsername(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //! Validate JWT Token
    public boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith((SecretKey) key())
                    .build().parse(token);

            return true;
        } catch (MalformedJwtException e) {
            throw new MovieAPIException( e.getMessage());
        }catch (ExpiredJwtException e1) {
            throw new MovieAPIException( "Expired JWT Token");
        }catch (UnsupportedJwtException e1) {
            throw new MovieAPIException( "Unsupported JWT Token");
        }catch (IllegalArgumentException e1) {
            throw new MovieAPIException( "JWT claims string is empty or null");
        }

    }
}
