package com.husseinelbhrawy.MovieAPI.Auth.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column( nullable = false)
    private  int otp;

    @Column( nullable = false)
    private Date expirationTime;

    @OneToOne
    private  User user;

    private  boolean isUsed = false;
}
