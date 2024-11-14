package com.husseinelbhrawy.MovieAPI.Auth.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(name = "name" , nullable = false  , unique = true)
    private  String name;


}
