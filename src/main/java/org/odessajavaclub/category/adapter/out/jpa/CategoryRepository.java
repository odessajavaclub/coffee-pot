package org.odessajavaclub.category.adapter.out.jpa;

import lombok.AllArgsConstructor;
import org.odessajavaclub.category.application.port.out.CreateCategoryPort;
import org.odessajavaclub.category.application.port.out.DeleteCategoryPort;
import org.odessajavaclub.category.application.port.out.LoadCategoriesPort;
import org.odessajavaclub.category.application.port.out.LoadCategoryPort;
import org.odessajavaclub.category.application.port.out.UpdateCategoryPort;
import org.odessajavaclub.category.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Component
class CategoryRepository implements CreateCategoryPort,
        UpdateCategoryPort,
        DeleteCategoryPort,
        LoadCategoryPort,
        LoadCategoriesPort {

    private CategoryJpaRepository jpaRepository;

    @Override
    public Category createCategory(Category category) {
        CategoryEntity entity = CategoryEntity.builder()
                .name(category.getName())
                .build();

        entity = jpaRepository.save(entity);
        return Category.withId(new Category.CategoryId(entity.getId()), entity.getName());
    }

    @Override
    public boolean deleteCategory(Category.CategoryId id) {
        CategoryEntity entity = CategoryEntity.builder()
                .id(id.getValue())
                .build();

        jpaRepository.delete(entity);

        return true;
    }

    @Override
    public Category updateCategory(Category category) {
        CategoryEntity entity = CategoryEntity.builder()
                .id(category.getId().getValue())
                .name(category.getName())
                .build();

        entity = jpaRepository.save(entity);

        return Category.withId(
                new Category.CategoryId(entity.getId()),
                entity.getName());
    }

    @Override
    public Page<Category> loadCategories(PageRequest pageRequest) {
        return jpaRepository.findAll(pageRequest)
                .map(entity ->
                        Category.withId(
                                new Category.CategoryId(entity.getId()),
                                entity.getName())
                );
    }

    @Override
    public Category loadCategory(Category.CategoryId id) {
        return jpaRepository.findById(id.getValue())
                .map(entity -> Category.withId(new Category.CategoryId(entity.getId()), entity.getName()))
                .orElseThrow(EntityNotFoundException::new);
    }


}
