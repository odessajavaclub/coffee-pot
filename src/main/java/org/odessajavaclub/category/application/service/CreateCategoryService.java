package org.odessajavaclub.category.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.category.application.port.in.CreateCategoryUseCase;
import org.odessajavaclub.category.application.port.out.CreateCategoryPort;
import org.odessajavaclub.category.domain.Category;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryService implements CreateCategoryUseCase {

    private final CreateCategoryPort createCategoryPort;

    @Override
    public Category createCategory(CreateCategoryCommand command) {
        checkNameNotEmpty(command);

        Category category = Category.withOutId(command.getName());
        return createCategoryPort.createCategory(category);
    }

    private void checkNameNotEmpty(CreateCategoryCommand command) {
        if (command.getName().isBlank()) {
            throw new BlankNameException();
        }
    }
}
