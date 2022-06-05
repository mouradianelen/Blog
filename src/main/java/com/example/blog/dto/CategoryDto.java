package com.example.blog.dto;

import com.example.blog.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CategoryDto {

    private String title;
    private String description;

    public static CategoryDto mapCategoryToDto(Category category){
        return CategoryDto.builder()
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }

    public static Category mapDtoToCategory(CategoryDto categoryDto){
        return Category.builder()
                .title(categoryDto.getTitle())
                .description(categoryDto.getDescription())
                .build();
    }
}


