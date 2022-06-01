package com.example.blog.service;

import com.example.blog.dto.PostDto;
import com.example.blog.entity.Post;
import com.example.blog.entity.UserEntity;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostDto createPost(PostDto postDto, String username){
        Post post = postRepository.save(PostDto.mapDtoToPost(postDto));
        UserEntity user = userRepository.findByUsername(username);
        user.getPosts().add(post);
        post.setAuthor(user);
        return PostDto.mapPostToDto(post);
    }
}
