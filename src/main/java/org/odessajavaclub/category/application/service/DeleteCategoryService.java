package org.odessajavaclub.category.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.category.application.port.in.DeleteCategoryUseCase;
import org.odessajavaclub.category.application.port.out.DeleteCategoryPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCategoryService implements DeleteCategoryUseCase {
    private final DeleteCategoryPort deleteCategoryPort;

    @Override
    public boolean deleteCategory(DeleteCategoryCommand command) {
        return deleteCategoryPort.deleteCategory(command.getId());
    }
}
