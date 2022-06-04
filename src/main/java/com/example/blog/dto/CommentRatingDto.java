package com.example.blog.dto;

import com.example.blog.entity.CommentRating;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentRatingDto {
    private String username;
    private int rating;
    private CommentDto comment;

    public static CommentRatingDto mapRatingToDto(CommentRating commentRating) {
        return CommentRatingDto.builder()
                .rating(commentRating.getRating())
                .username(commentRating.getUser().getUsername())
                .build();
    }

    public static CommentRating mapDtoToRating(CommentRatingDto commentRatingDto) {
        return CommentRating.builder()
                .rating(commentRatingDto.getRating())
                .build();
    }
}
