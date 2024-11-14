package com.husseinelbhrawy.MovieAPI.Repository;

import com.husseinelbhrawy.MovieAPI.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
