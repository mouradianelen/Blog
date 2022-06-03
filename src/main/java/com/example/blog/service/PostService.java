package com.example.blog.service;

import com.example.blog.dto.CommentDto;
import com.example.blog.dto.PostDto;
import com.example.blog.dto.UserDto;
import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.entity.UserEntity;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public PostDto createPost(PostDto postDto, String username){
        Post post = postRepository.save(PostDto.mapDtoToPost(postDto));
        UserEntity user = userRepository.findByUsername(username);
        user.getPosts().add(post);
        post.setAuthor(user);
        return PostDto.mapPostToDto(post);
    }

    @Transactional
    public CommentDto addComment(CommentDto commentDto, Long postId){
        Comment comment = commentRepository.save(CommentDto.mapDtoToComment(commentDto));
        Optional<Post> post = postRepository.findById(postId);
        UserEntity user = userRepository.findByUsername(commentDto.getAuthorUsername());
        comment.setPost(post.get());
        comment.setAuthor(user);
        return CommentDto.mapCommentToDto(comment);
    }

    public List<PostDto> getPost(String username){
        List<Post> post = postRepository.findPostsByAuthorUsername(username);
        List<PostDto> postDtos = PostDto.mapPostToDto(post);
        return postDtos;
    }
}
