package org.odessajavaclub.category.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.category.application.port.in.CreateCategoryUseCase;
import org.odessajavaclub.category.application.port.in.DeleteCategoryUseCase;
import org.odessajavaclub.category.application.port.in.GetCategoriesQuery;
import org.odessajavaclub.category.application.port.in.GetCategoryQuery;
import org.odessajavaclub.category.application.port.in.UpdateCategoryUseCase;
import org.odessajavaclub.category.domain.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/category")
public class CategoryController {

    private final GetCategoriesQuery getCategoriesQuery;
    private final GetCategoryQuery getCategoryQuery;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @GetMapping("/list")
    public List<CategoryDto> getAll() {
        return getCategoriesQuery.getCategories().stream()
                .map(CategoryDtoMapper::toDto)
                .collect(toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
        return getCategoryQuery.getCategory(new GetCategoryQuery.CategoryQuery(new Category.CategoryId(id)))
                .map(CategoryDtoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return deleteCategoryUseCase.deleteCategory(new DeleteCategoryUseCase.DeleteCategoryCommand(new Category.CategoryId(id)))
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        return updateCategoryUseCase.updateCategory(new UpdateCategoryUseCase.UpdateCategoryCommand(new Category.CategoryId(id), categoryDto.getName()))
                .map(CategoryDtoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto categoryDto) {
        Category result = createCategoryUseCase.createCategory(new CreateCategoryUseCase.CreateCategoryCommand(categoryDto.getName()));
        return CategoryDtoMapper.toDto(result);
    }
}
