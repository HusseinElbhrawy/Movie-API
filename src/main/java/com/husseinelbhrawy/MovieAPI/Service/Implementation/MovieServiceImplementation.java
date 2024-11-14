package com.husseinelbhrawy.MovieAPI.Service.Implementation;


import com.husseinelbhrawy.MovieAPI.Entity.Movie;
import com.husseinelbhrawy.MovieAPI.Exceptions.MovieNotFoundException;
import com.husseinelbhrawy.MovieAPI.Payload.MovieDTO;
import com.husseinelbhrawy.MovieAPI.Payload.MoviePageResponse;
import com.husseinelbhrawy.MovieAPI.Repository.MovieRepository;
import com.husseinelbhrawy.MovieAPI.Service.Base.FileService;
import com.husseinelbhrawy.MovieAPI.Service.Base.MovieService;
import com.husseinelbhrawy.MovieAPI.Utils.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class MovieServiceImplementation implements MovieService {
    private  final MovieRepository movieRepository;
    private  final FileService fileService;
    private  final ModelMapper modelMapper;

    @Autowired
    public MovieServiceImplementation(MovieRepository movieRepository, FileService fileService, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
        this.modelMapper = modelMapper;
    }

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO, MultipartFile poster , HttpServletRequest request) throws IOException {
        String uploadFileName = fileService.uploadFile(AppConstants.DEFAULT_POSTER_PATH, poster);
        try {
            //! 1. Upload File

            //! 2. set the value of field 'poster' as fileName
            movieDTO.setPoster(uploadFileName);
            //! 3. map dto to Movie Object
            Movie movie = modelMapper.map(movieDTO, Movie.class);

            //! 4. Save the Movie Object => saved Movie Object
            Movie savedMovie = movieRepository.save(movie);
            //! 5. generate the posterUrl using saved Movie Object
            String posterUrl1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + AppConstants.DEFAULT_API_FILES + movie.getPoster();

            //! 6. map Movie Object to MovieDTO and return MovieDTO
            MovieDTO response =modelMapper.map(savedMovie, MovieDTO.class);
            response.setPosterUrl(posterUrl1);
            return response;
        }catch (DataIntegrityViolationException e){
            if (uploadFileName != null) {
                fileService.deleteFile(AppConstants.DEFAULT_POSTER_PATH, uploadFileName);
            }
            throw new DataIntegrityViolationException("Movie Already Exists , Please enter a new one! ");

        }

    }

    @Override
    public MovieDTO findByMovieById(int id ) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie with Id : " + id + " Not Found"));

        String posterUrl = AppConstants.BASE_URL + AppConstants.DEFAULT_API_FILES  + movie.getPoster();
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
        movieDTO.setPosterUrl(posterUrl);
        return movieDTO;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> {
            MovieDTO currentMovie= modelMapper.map(movie, MovieDTO.class);
            currentMovie.setPosterUrl(AppConstants.BASE_URL + AppConstants.DEFAULT_API_FILES  + movie.getPoster());
            return currentMovie;
        }).toList();
    }

    @Override

    public MovieDTO updateMovie(int id, MovieDTO movieDTO, MultipartFile poster) throws IOException {


        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie Not Found"));

        //! Update other fields
        movie.setTitle(movieDTO.getTitle());
        movie.setDirector(movieDTO.getDirector());
        movie.setStudio(movieDTO.getStudio());
        movie.setMovieCast(movieDTO.getMovieCast());
        movie.setReleaseYear(movieDTO.getReleaseYear());

        if (poster != null && !poster.isEmpty()) {
            Files.deleteIfExists(Path.of(AppConstants.DEFAULT_POSTER_PATH + movie.getPoster()));
            String uploadFileName = fileService.uploadFile(AppConstants.DEFAULT_POSTER_PATH, poster);
            movie.setPoster(uploadFileName);
        }
        MovieDTO response = modelMapper.map(movieRepository.save(movie), MovieDTO.class);
        response.setPosterUrl(AppConstants.BASE_URL + AppConstants.DEFAULT_API_FILES + movie.getPoster());
        return response;
    }


    @Override
    public String deleteMovie(int id) throws IOException {
        MovieDTO movie = findByMovieById(id);
        Files.deleteIfExists(Path.of(AppConstants.DEFAULT_POSTER_PATH + movie.getPoster()));
        movieRepository.deleteById(movie.getId());
        return "Movies with Id : " + id + " has been deleted successfully:)";
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Movie> moviesPage =   movieRepository.findAll(pageable);

        List<Movie> movies = moviesPage.getContent();

        List<MovieDTO> movieDTOS = movies.stream().map(movie -> {
            MovieDTO currentMovie= modelMapper.map(movie, MovieDTO.class);
            currentMovie.setPosterUrl(AppConstants.BASE_URL + AppConstants.DEFAULT_API_FILES  + movie.getPoster());
            return currentMovie;
        }).toList();

        return new MoviePageResponse(movieDTOS, pageNo, pageSize, movieRepository.count() , moviesPage.getTotalPages(), moviesPage.isLast());
    }

    @Override
    public MoviePageResponse getAllMoviesWithPaginationAndSorting(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by(sortDir).ascending() : Sort.by(sortDir).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Movie> moviesPage =   movieRepository.findAll(pageable);

        List<Movie> movies = moviesPage.getContent();

        List<MovieDTO> movieDTOS = movies.stream().map(movie -> {
            MovieDTO currentMovie= modelMapper.map(movie, MovieDTO.class);
            currentMovie.setPosterUrl(AppConstants.BASE_URL + AppConstants.DEFAULT_API_FILES  + movie.getPoster());
            return currentMovie;
        }).toList();

        return new MoviePageResponse(movieDTOS, pageNo, pageSize, movieRepository.count() , moviesPage.getTotalPages(), moviesPage.isLast());
    }
}
