package com.example.blog.repository;

import com.example.blog.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "select * from blog.user u where u.enabled = true and u.username =:username",nativeQuery = true)
    Optional<UserEntity> findByUsername(String username);
}
