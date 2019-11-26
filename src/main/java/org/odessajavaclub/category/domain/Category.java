package org.odessajavaclub.category.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class Category {
    private CategoryId id;

    @NotNull
    @NotBlank
    private String name;

    @Value
    public static class CategoryId {
        @NotNull
        private Long value;
    }

    public static Category withId(CategoryId id, String name) {
        return new Category(id, name);
    }

    public static Category withOutId(String name) {
        return new Category(null, name);
    }

    public static Category from(Category category, String newName) {
        return new Category(category.id, newName);
    }
}
