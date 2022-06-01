package com.example.blog.dto;

import com.example.blog.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class CommentDto {
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public static CommentDto mapCommentToDto(Comment comment) {
        return CommentDto.builder()
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();

    }
    public static Comment mapDtoToComment(CommentDto commentDto){
        return Comment.builder()
                .content(commentDto.getContent())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }
    public static List<CommentDto> mapCommentToDto(List<Comment> comments){
        return comments.stream().map(CommentDto::mapCommentToDto).collect(Collectors.toList());
    }
    public static List<Comment> mapDtoToComment(List<CommentDto> commentDtos){
        return commentDtos.stream().map(CommentDto::mapDtoToComment).collect(Collectors.toList());
    }
}
