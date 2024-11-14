package com.husseinelbhrawy.MovieAPI.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "movies" )

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column(nullable = false , unique = true )
    @NotBlank(message = "Please Provide Movie's Title..")
    private  String title;

    @Column(nullable = false)
    @NotBlank(message = "Please Provide Movie's Director..")
    private  String director;

    @Column(nullable = false)
    @NotBlank(message = "Please Provide Movie's Studio..")
    private  String studio;


    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false)
    @NotNull(message = "Please Provide Movie's Release Year..")
    private  int releaseYear;


    @Column(nullable = false)
    @NotBlank(message = "Please Provide Movie's Poster..")
    private  String poster;


}
