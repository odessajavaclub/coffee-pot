package org.odessajavaclub.category.application.port.in;

import org.odessajavaclub.category.domain.Category;

import java.util.List;

public interface GetCategoriesQuery {
    List<Category> getCategories();
}
