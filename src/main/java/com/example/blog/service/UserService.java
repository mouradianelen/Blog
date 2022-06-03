package com.example.blog.service;

import com.example.blog.dto.CommentDto;
import com.example.blog.dto.PostDto;
import com.example.blog.dto.UserDto;
import com.example.blog.entity.Post;
import com.example.blog.entity.UserEntity;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserDto createUser(UserDto userDto){
        UserEntity user = userRepository.save(UserDto.mapDtoToUser(userDto));
        user.setEnabled(true);
        return UserDto.mapUserToDto(user);
    }

    public UserDto getUser(String username){
        UserEntity user = userRepository.findByUsername(username);
        UserDto userDto = UserDto.mapUserToDto(user);
        List<PostDto> postDtos = PostDto.mapPostToDto(user.getPosts());
        postDtos.forEach(postDto -> getPost(postDto.getPostId()));
        userDto.setPosts(postDtos);
        return userDto;
    }
    private void getPost(Long id){
        Optional<Post> post = postRepository.findById(id);
        PostDto postDto = PostDto.mapPostToDto(post.get());
        List<CommentDto> commentDtos = CommentDto.mapCommentToDto(post.get().getComments());
        postDto.setComments(commentDtos);
    }
}
