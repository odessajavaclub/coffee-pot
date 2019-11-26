package org.odessajavaclub.category.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.category.domain.Category;
import org.odessajavaclub.shared.SelfValidating;

import javax.validation.constraints.NotNull;

public interface DeleteCategoryUseCase {
    boolean deleteCategory(DeleteCategoryCommand id);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class DeleteCategoryCommand extends SelfValidating<DeleteCategoryUseCase.DeleteCategoryCommand> {

        @NotNull
        private final Category.CategoryId id;

        public DeleteCategoryCommand(Category.CategoryId id) {
            this.id = id;
            validateSelf();
        }

    }
}
