package com.example.blog.service;

import com.example.blog.dto.CommentDto;
import com.example.blog.dto.CommentRatingDto;
import com.example.blog.dto.PostDto;
import com.example.blog.entity.Comment;
import com.example.blog.entity.CommentRating;
import com.example.blog.entity.Post;
import com.example.blog.entity.UserEntity;
import com.example.blog.exception.CommentNotFoundException;
import com.example.blog.exception.PostNotFoundException;
import com.example.blog.exception.RatingOutOfBoundsException;
import com.example.blog.exception.UserNotFoundException;
import com.example.blog.repository.CommentRatingRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentRatingRepository commentRatingRepository;

    public PostDto createPost(PostDto postDto, String username) {
        Post post = PostDto.mapDtoToPost(postDto);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException(username);
        user.get().getPosts().add(post);
        post.setAuthor(user.get());
        post.setIsActive(true);
        return PostDto.mapPostToDto(postRepository.save(post));
    }

    public CommentDto addComment(CommentDto commentDto, Long postId) {
        Comment comment = CommentDto.mapDtoToComment(commentDto);
        Optional<Post> post = postRepository.findById(postId);
        Optional<UserEntity> user = userRepository.findByUsername(commentDto.getAuthorUsername());

        if (user.isEmpty())
            throw new UserNotFoundException(commentDto.getAuthorUsername());
        if (post.isEmpty())
            throw new PostNotFoundException(postId);

        comment.setPost(post.get());
        comment.setAuthor(user.get());
        return CommentDto.mapCommentToDto(commentRepository.save(comment));

    }

    public List<PostDto> getPostsByUsername(String username) {
        List<Post> posts = postRepository.findPostsByAuthorUsername(username);
        return PostDto.mapPostToDto(posts);
    }

    public List<PostDto> getSortedPosts() {
        return PostDto.mapPostToDto(postRepository.findSortedPosts());
    }

    @Transactional
    public CommentDto editComment(CommentDto commentDto, Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty())
            throw new CommentNotFoundException(commentId);
        comment.get().setContent(commentDto.getContent());
        comment.get().setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return CommentDto.mapCommentToDto(comment.get());
    }

    public CommentRatingDto rateComment(CommentRatingDto commentRatingDto, Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        Optional<UserEntity> user = userRepository.findByUsername(commentRatingDto.getUsername());

        if (comment.isEmpty())
            throw new CommentNotFoundException(commentId);

        if (user.isEmpty())
            throw new UserNotFoundException(commentRatingDto.getUsername());
        if (commentRatingDto.getRating() > 5 || commentRatingDto.getRating() < 1)
            throw new RatingOutOfBoundsException();

        CommentRating commentRating = CommentRatingDto.mapDtoToRating(commentRatingDto);
        commentRating.setComment(comment.get());
        commentRating.setUser(user.get());
        commentRatingDto.setComment(CommentDto.mapCommentToDto(comment.get()));
        commentRatingRepository.save(commentRating);
        return commentRatingDto;

    }

}
