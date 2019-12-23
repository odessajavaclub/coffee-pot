package org.odessajavaclub.category.application.port.out;

import org.odessajavaclub.category.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface LoadCategoriesPort {
    Page<Category> loadCategories(PageRequest pageRequest);
}
