package com.husseinelbhrawy.MovieAPI.Payload;

import java.util.List;

public record MoviePageResponse(List<MovieDTO> content ,
                                int pageNumber ,
                                int size ,
                                long totalElements ,
                                int totalPages,
                                boolean isLast) {
}
