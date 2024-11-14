package com.husseinelbhrawy.MovieAPI.Auth.Security;


import com.husseinelbhrawy.MovieAPI.Auth.Entity.RefreshToken;
import com.husseinelbhrawy.MovieAPI.Auth.Entity.User;
import com.husseinelbhrawy.MovieAPI.Auth.Repository.RefreshTokenRepository;
import com.husseinelbhrawy.MovieAPI.Auth.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServices {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

//    @Value("${jwt-refresh-expiration-milliseconds}")
//    private  long jwtRefreshExpirationDate;


    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + username));

        RefreshToken token = user.getRefreshToken();

        if (token == null) {
            long refreshTokenValidity = 604800000;
            RefreshToken  newToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();

            refreshTokenRepository.save(newToken);
            return  newToken;
        }

        return token;
    }

    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found!"));

        if (refToken.getExpirationTime().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refToken);
            throw new RuntimeException("Refresh Token expired");
        }

        return refToken;
    }


}
