package org.odessajavaclub.category.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.category.domain.Category;
import org.odessajavaclub.shared.SelfValidating;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface CreateCategoryUseCase {

    Category createCategory(CreateCategoryCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class CreateCategoryCommand extends SelfValidating<CreateCategoryCommand> {

        @NotNull
        @NotBlank
        private final String name;

        public CreateCategoryCommand(String name) {
            this.name = name;
            validateSelf();
        }
    }
}
