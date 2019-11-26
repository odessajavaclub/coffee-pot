package org.odessajavaclub.category.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.category.application.port.in.UpdateCategoryUseCase;
import org.odessajavaclub.category.application.port.out.LoadCategoryPort;
import org.odessajavaclub.category.application.port.out.UpdateCategoryPort;
import org.odessajavaclub.category.domain.Category;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateCategoryService implements UpdateCategoryUseCase {

    private final LoadCategoryPort loadCategoryPort;
    private final UpdateCategoryPort updateCategoryPort;

    @Override
    public Optional<Category> updateCategory(UpdateCategoryCommand command) {
        Category.CategoryId id = Objects.requireNonNull(command.getId(), "CategoryId can't be null");

        Category loadedCategory = loadCategoryPort.loadCategory(id);
        if (loadedCategory == null) {
            return Optional.empty();
        }

        Category updatedCategory = Category.from(loadedCategory, command.getName());

        return Optional.ofNullable(updateCategoryPort.updateCategory(updatedCategory));
    }
}
