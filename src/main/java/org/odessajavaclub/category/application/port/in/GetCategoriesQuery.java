package org.odessajavaclub.category.application.port.in;

import org.odessajavaclub.category.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface GetCategoriesQuery {
    Page<Category> getCategories(PageRequest pageRequest);
}
