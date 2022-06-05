package com.example.blog.dto;

import com.example.blog.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class UserDto {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private List<PostDto> posts;

    public static UserDto mapUserToDto(UserEntity user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .posts(PostDto.mapPostToDto(user.getPosts()))
                .build();
    }

    public static UserEntity mapDtoToUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        return user;
    }

    public static List<UserDto> mapUserToDto(List<UserEntity> users) {
        return users.stream().map(UserDto::mapUserToDto).collect(Collectors.toList());
    }

    public static List<UserEntity> mapDtoToUser(List<UserDto> userDtos) {
        return userDtos.stream().map(UserDto::mapDtoToUser).collect(Collectors.toList());
    }
}
