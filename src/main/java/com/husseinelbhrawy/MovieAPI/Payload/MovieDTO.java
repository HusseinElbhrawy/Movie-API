package com.husseinelbhrawy.MovieAPI.Payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private  int id;

    @NotBlank(message = "Please Provide Movie's Title..")
    private  String title;

    @NotBlank(message = "Please Provide Movie's Director..")
    private  String director;

    @NotBlank(message = "Please Provide Movie's Studio..")
    private  String studio;

    private Set<String> movieCast;

    private int releaseYear;


    //    @NotBlank(message = "Please Provide Movie's Poster..")
    private  String poster;

    //    @NotBlank(message = "Please Provide Movie's Poster URL..")
    private  String posterUrl;
}
