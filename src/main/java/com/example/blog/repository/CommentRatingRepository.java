package com.example.blog.repository;

import com.example.blog.entity.CommentRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRatingRepository extends JpaRepository<CommentRating,Long> {
}
