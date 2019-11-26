package org.odessajavaclub.category.application.port.out;

import org.odessajavaclub.category.domain.Category;

public interface DeleteCategoryPort {
    boolean deleteCategory(Category.CategoryId id);
}
