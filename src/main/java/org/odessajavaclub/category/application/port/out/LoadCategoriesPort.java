package org.odessajavaclub.category.application.port.out;

import org.odessajavaclub.category.domain.Category;

import java.util.List;

public interface LoadCategoriesPort {
    List<Category> loadCategories();
}
