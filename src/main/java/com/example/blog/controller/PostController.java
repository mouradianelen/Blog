package com.example.blog.controller;

import com.example.blog.dto.PostDto;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/{username}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable("username") String username){
        return ResponseEntity.ok().body(postService.createPost(postDto,username));
    }
}
