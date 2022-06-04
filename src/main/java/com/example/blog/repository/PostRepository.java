package com.example.blog.repository;

import com.example.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select * from blog.post p inner join blog.user u " +
            "on p.user_id = u.id where p.is_active = true and u.username=:username and u.enabled = true",
            nativeQuery = true)
    List<Post> findPostsByAuthorUsername(String username);

    @Query(value = "select * from blog.post p where p.is_active = true " +
            "order by p.created_at desc",
            nativeQuery = true)
    List<Post> findSortedPosts();
}
