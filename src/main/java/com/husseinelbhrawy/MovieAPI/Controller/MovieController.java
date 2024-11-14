package com.husseinelbhrawy.MovieAPI.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.husseinelbhrawy.MovieAPI.Payload.MovieDTO;
import com.husseinelbhrawy.MovieAPI.Service.Base.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@Tag(name = "Movie Controller")
public class MovieController {

    private  final MovieService movieService;






    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MovieDTO> addMovie(@Valid @ModelAttribute MovieDTO movieDTO,
                                             @RequestPart("image") MultipartFile poster ,
                                             HttpServletRequest request) throws IOException {
        return  new ResponseEntity<>( movieService.addMovie(movieDTO , poster, request) , HttpStatus.CREATED);
    }

    private MovieDTO convertToMovieDTO(String movieDtoObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObject, MovieDTO.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Get All Movies"

    )
    public List<MovieDTO> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovieDTO getMovieById(@PathVariable int id){
        return movieService.findByMovieById(id);
    }


    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public MovieDTO updateMovie(@ModelAttribute("id") int id,
                                @Valid @ModelAttribute MovieDTO movieDTO,
                                 @RequestPart(value = "image" ,required = false) MultipartFile poster) throws IOException {
        return movieService.updateMovie(id,movieDTO ,poster);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)

    public Map<String ,String> deleteMovie(@RequestParam int id) throws IOException {
        String message= movieService.deleteMovie(id);

        return Map.of("message",message);
    }

    @PostMapping("/test-upload")
    public ResponseEntity<String> testUpload(@RequestPart(value = "image", required = false) MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            return ResponseEntity.ok("File received: " + image.getOriginalFilename());
        } else {
            return ResponseEntity.ok("No file uploaded");
        }
    }





}
