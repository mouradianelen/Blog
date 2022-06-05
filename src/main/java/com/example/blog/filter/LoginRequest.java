package com.example.blog.filter;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LoginRequest implements Serializable {

    private String username;
    private String password;

}
