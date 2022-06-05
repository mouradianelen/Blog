package com.example.blog.controller;

import com.example.blog.dto.UserDto;
import com.example.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userService.createUser(userDto));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok().body(userService.getSortedUsers());
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.deleteUser(username));
    }


}
