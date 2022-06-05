package com.example.blog.controller;

import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.CommentDto;
import com.example.blog.dto.CommentRatingDto;
import com.example.blog.dto.PostDto;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PreAuthorize("#username == authentication.name or hasRole('ROLE_ADMIN')")
    @PostMapping("/{username}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable("username") String username) {
        return ResponseEntity.ok().body(postService.createPost(postDto, username));
    }

    @PostMapping("/comments/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable("postId") Long postId) {
        return ResponseEntity.ok().body(postService.addComment(commentDto, postId));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<PostDto>> getPosts(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(postService.getPostsByUsername(username));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<PostDto>> getSortedPosts() {
        return ResponseEntity.ok().body(postService.getSortedPosts());
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> editComment(@RequestBody CommentDto commentDto,
                                                  @PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok().body(postService.editComment(commentDto, commentId));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok().body(postService.deleteComment(commentId));
    }

    @PostMapping("/comments/rate/{commentId}")
    public ResponseEntity<CommentRatingDto> rateComment(@RequestBody CommentRatingDto commentRatingDto,
                                                        @PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok().body(postService.rateComment(commentRatingDto, commentId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok().body(postService.createCategory(categoryDto));
    }

    @PostMapping("/add-category/{postId}")
    public ResponseEntity<PostDto> addCategoryToPost(@RequestBody CategoryDto categoryDto,
                                                     @PathVariable("postId") Long postId) {
        return ResponseEntity.ok().body(postService.addCategoryToPost(categoryDto, postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDto> deletePost(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok().body(postService.deletePost(postId));
    }


}
