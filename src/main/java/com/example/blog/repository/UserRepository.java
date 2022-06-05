package com.example.blog.repository;

import com.example.blog.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "select * from blog.user u where u.enabled = true and u.username =:username",nativeQuery = true)
    Optional<UserEntity> findByUsername(String username);

    @Query(value = "select * from blog.user u where u.enabled = true " +
            "order by u.id desc", nativeQuery = true)
    List<UserEntity> findAllSorted();
    @Query(value = "select * from blog.user u where u.enabled = true and u.email =:email",nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);
}
