package com.husseinelbhrawy.MovieAPI.Utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConstants {

    @Value("${project.poster}")
    private String defaultPosterPath;

    @Value("${base.url}")
    private String baseURL;



    public static String DEFAULT_POSTER_PATH;
    public static String BASE_URL;
    public static final String  DEFAULT_API_FILES = "/api/posters/";

    @PostConstruct
    public void init() {
        DEFAULT_POSTER_PATH = defaultPosterPath;
        BASE_URL = baseURL;
    }


}
