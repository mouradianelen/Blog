package com.example.blog.service;

import com.example.blog.dto.PostDto;
import com.example.blog.dto.UserDto;
import com.example.blog.entity.Role;
import com.example.blog.entity.UserEntity;
import com.example.blog.exception.EmailAlreadyExistsException;
import com.example.blog.exception.UserNotFoundException;
import com.example.blog.exception.UsernameAlreadyExistsException;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserDto createUser(UserDto userDto) {
        UserEntity user = userRepository.save(UserDto.mapDtoToUser(userDto));
        user.setEnabled(true);
        return UserDto.mapUserToDto(user);
    }

    public UserDto getUser(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException(username);
        UserDto userDto = UserDto.mapUserToDto(user.get());
        List<PostDto> postDtos = PostDto.mapPostToDto(user.get().getPosts());
        userDto.setPosts(postDtos);
        return userDto;
    }

    @Transactional
    public UserDto deleteUser(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException(username);
        UserDto userDto = UserDto.mapUserToDto(user.get());
        user.get().setEnabled(false);
        user.get().getPosts().forEach(post -> post.setIsActive(false));
        return userDto;
    }

    public List<UserDto> getSortedUsers() {
        return UserDto.mapUserToDto(userRepository.findAllSorted());
    }

    @Transactional
    public UserDto registerNewUserAccount(UserDto userDTO) {

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(userDTO.getEmail());
        }
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(userDTO.getUsername());
        }

        UserEntity user = UserDto.mapDtoToUser(userDTO);
        System.out.println(user.getUsername());

        Role role = roleRepository.findByName("ROLE_USER");
        System.out.println(role.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(true);
        user.getRoles().add(role);
        return UserDto.mapUserToDto(userRepository.save(user));
    }
}
