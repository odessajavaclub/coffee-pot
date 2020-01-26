package org.odessajavaclub.category.adapter.in.rest;

import org.odessajavaclub.category.domain.Category;

import java.util.Optional;

class CategoryDtoMapper {
    static CategoryDto toDto(Category category) {
        return new CategoryDto.CategoryDtoBuilder()
                .name(category.getName())
                .id(Optional.ofNullable(category.getId())
                        .map(Category.CategoryId::getValue)
                        .orElseGet(null))
                .build();
    }

    Category fromDto(CategoryDto dto) {
        return Category.withId(new Category.CategoryId(dto.getId()), dto.getName());
    }
}
