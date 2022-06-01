package com.example.blog.service;

import com.example.blog.dto.PostDto;
import com.example.blog.dto.UserDto;
import com.example.blog.entity.UserEntity;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(UserDto userDto){
        UserEntity user = userRepository.save(UserDto.mapDtoToUser(userDto));
        user.setEnabled(true);
        return UserDto.mapUserToDto(user);
    }

    public UserDto getUser(String username){
        UserEntity user = userRepository.findByUsername(username);
        UserDto userDto = UserDto.mapUserToDto(user);
        userDto.setPosts(PostDto.mapPostToDto(user.getPosts()));
        return userDto;
    }
}
