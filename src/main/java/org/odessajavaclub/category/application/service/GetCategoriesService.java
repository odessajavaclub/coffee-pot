package org.odessajavaclub.category.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.category.application.port.in.GetCategoriesQuery;
import org.odessajavaclub.category.application.port.in.GetCategoryQuery;
import org.odessajavaclub.category.application.port.out.LoadCategoriesPort;
import org.odessajavaclub.category.application.port.out.LoadCategoryPort;
import org.odessajavaclub.category.domain.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetCategoriesService implements GetCategoryQuery, GetCategoriesQuery {

    private final LoadCategoryPort loadCategoryPort;
    private final LoadCategoriesPort loadCategoriesPort;

    @Override
    public List<Category> getCategories() {
        return loadCategoriesPort.loadCategories();
    }

    @Override
    public Optional<Category> getCategory(CategoryQuery query) {
        return Optional.ofNullable(loadCategoryPort.loadCategory(query.getId()));
    }
}
