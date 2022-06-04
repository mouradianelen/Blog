package com.example.blog.service;

import com.example.blog.dto.PostDto;
import com.example.blog.dto.UserDto;
import com.example.blog.entity.UserEntity;
import com.example.blog.exception.UserNotFoundException;
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

    public UserDto createUser(UserDto userDto){
        UserEntity user = userRepository.save(UserDto.mapDtoToUser(userDto));
        user.setEnabled(true);
        return UserDto.mapUserToDto(user);
    }

    public UserDto getUser(String username){
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException(username);
        UserDto userDto = UserDto.mapUserToDto(user.get());
        List<PostDto> postDtos = PostDto.mapPostToDto(user.get().getPosts());
        userDto.setPosts(postDtos);
        return userDto;
    }

    @Transactional
    public UserDto deleteUser(String username){
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException(username);
        UserDto userDto = UserDto.mapUserToDto(user.get());
        user.get().setEnabled(false);
        return userDto;

    }
}
