package org.odessajavaclub.category.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.category.domain.Category;
import org.odessajavaclub.shared.SelfValidating;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UpdateCategoryUseCase {
    Optional<Category> updateCategory(UpdateCategoryCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class UpdateCategoryCommand extends SelfValidating<UpdateCategoryCommand> {

        @NotNull
        private final Category.CategoryId id;

        @NotNull
        @NotBlank
        private final String name;

        public UpdateCategoryCommand(Category.CategoryId id, String name) {
            this.id = id;
            this.name = name;
            validateSelf();
        }
    }
}
