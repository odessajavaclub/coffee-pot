package org.odessajavaclub.category.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.category.domain.Category;
import org.odessajavaclub.shared.SelfValidating;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface GetCategoryQuery {
    Optional<Category> getCategory(CategoryQuery query);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class CategoryQuery extends SelfValidating<CategoryQuery> {
        @NotNull
        private final Category.CategoryId id;

        public CategoryQuery(Category.CategoryId id) {
            this.id = id;
            validateSelf();
        }
    }
}
