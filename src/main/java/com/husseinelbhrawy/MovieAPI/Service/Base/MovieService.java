package com.husseinelbhrawy.MovieAPI.Service.Base;

import com.husseinelbhrawy.MovieAPI.Payload.MovieDTO;
import com.husseinelbhrawy.MovieAPI.Payload.MoviePageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MovieDTO addMovie(MovieDTO movieDTO, MultipartFile poster , HttpServletRequest request) throws IOException;

    MovieDTO findByMovieById(int id);

    List<MovieDTO> getAllMovies();

    MovieDTO updateMovie(int id ,MovieDTO movieDTO, MultipartFile poster) throws IOException;

    String deleteMovie(int id) throws IOException;

    MoviePageResponse getAllMoviesWithPagination(int pageNo, int pageSize);

    MoviePageResponse getAllMoviesWithPaginationAndSorting(int pageNo, int pageSize, String sortBy, String sortDir);

//    MovieDTO getMovieByName(String name);
//
//    MovieDTO getMovieByGenre(String genre);
//
//    MovieDTO getMovieByYear(int year);
//
//    MovieDTO getMovieByRating(float rating);
//
//    MovieDTO getMovieByDirector(String director);
//
//    MovieDTO getMovieByActor(String actor);



}
