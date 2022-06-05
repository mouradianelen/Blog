package com.example.blog.service;

import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.CommentDto;
import com.example.blog.dto.CommentRatingDto;
import com.example.blog.dto.PostDto;
import com.example.blog.entity.*;
import com.example.blog.exception.*;
import com.example.blog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
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
    private final CategoryRepository categoryRepository;

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

    @Transactional
    public PostDto deletePost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty())
            throw new PostNotFoundException(postId);
        PostDto postDto = PostDto.mapPostToDto(post.get());
        post.get().setIsActive(false);
        return postDto;
    }
    public CommentDto deleteComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty())
            throw new CommentNotFoundException(commentId);
        commentRepository.delete(comment.get());
        return CommentDto.mapCommentToDto(comment.get());
    }

    public CategoryDto createCategory(CategoryDto categoryDto){
        if(categoryRepository.existsByTitle(categoryDto.getTitle()))
            return CategoryDto.mapCategoryToDto(categoryRepository.findByTitle(categoryDto.getTitle()));
        Category category = CategoryDto.mapDtoToCategory(categoryDto);
        return CategoryDto.mapCategoryToDto(categoryRepository.save(category));
    }

    @Transactional
    public PostDto addCategoryToPost(CategoryDto categoryDto, Long postId){
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty())
            throw new PostNotFoundException(postId);
        if(!categoryRepository.existsByTitle(categoryDto.getTitle()))
            throw new NoSuchCategoryException(categoryDto.getTitle());
        post.get().getCategories().add(categoryRepository.findByTitle(categoryDto.getTitle()));
        return PostDto.mapPostToDto(post.get());
    }

}
