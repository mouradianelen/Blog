package com.example.blog.dto;

import com.example.blog.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class PostDto {
    private Long postId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String text;
    private String author;
    private List<CommentDto> comments;

    public static PostDto mapPostToDto(Post post){
        return PostDto.builder()
                .postId(post.getId())
                .text(post.getText())
                .createdAt(post.getCreatedAt())
                .author(post.getAuthor().getUsername())
                .comments(CommentDto.mapCommentToDto(post.getComments()))
                .build();
    }

    public static Post mapDtoToPost(PostDto postDto){
        return Post.builder()
                .text(postDto.getText())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }
    public static List<PostDto> mapPostToDto(List<Post> posts){
        return posts.stream().map(PostDto::mapPostToDto).collect(Collectors.toList());
    }
    public static List<Post> mapDtoToPost(List<PostDto> postDtos){
        return postDtos.stream().map(PostDto::mapDtoToPost).collect(Collectors.toList());
    }
}
