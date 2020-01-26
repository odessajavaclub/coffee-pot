package org.odessajavaclub.category.adapter.in.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.category.domain.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestCategoryDtoMapperTest {

    private CategoryDtoMapper categoryDtoMapper;

    @BeforeEach
    private void setUp() {
        categoryDtoMapper = new CategoryDtoMapper();
    }

    @Test
    public void toGetCategoryDto() {
        CategoryDto dto = CategoryDto.builder()
                .id(1L)
                .name("category #1")
                .build();

        Category expected = Category.withId(new Category.CategoryId(1L), "category #1");
        Category actual = categoryDtoMapper.fromDto(dto);

        assertEquals(expected, actual);
    }
}
