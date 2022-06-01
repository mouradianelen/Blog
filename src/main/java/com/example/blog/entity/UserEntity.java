package com.example.blog.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user", schema = "blog")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private Boolean enabled;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> posts = new LinkedList<>();

    public UserEntity(){

    }

}
