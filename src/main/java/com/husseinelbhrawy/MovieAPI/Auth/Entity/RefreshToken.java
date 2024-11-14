package com.husseinelbhrawy.MovieAPI.Auth.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int td;

    @Column(nullable = false , unique = true , length = 500)
    @NotBlank(message = "Please Provide Refresh Token..")
    private  String refreshToken;

    @Column(nullable = false)
    private Instant expirationTime;

    @OneToOne
    private  User user;
}
