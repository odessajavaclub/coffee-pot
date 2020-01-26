package org.odessajavaclub.category.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.category.application.port.in.CreateCategoryUseCase;
import org.odessajavaclub.category.application.port.in.DeleteCategoryUseCase;
import org.odessajavaclub.category.application.port.in.GetCategoriesQuery;
import org.odessajavaclub.category.application.port.in.GetCategoryQuery;
import org.odessajavaclub.category.application.port.in.UpdateCategoryUseCase;
import org.odessajavaclub.category.domain.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/category")
public class CategoryController {

    @Value("${spring.data.web.pageable.default-page-size}")
    private int defaultPageSize;

    private final GetCategoriesQuery getCategoriesQuery;
    private final GetCategoryQuery getCategoryQuery;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @GetMapping
    public Page<CategoryDto> getCategories(@RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size) {
        PageRequest pageRequest = PageRequest.of(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(defaultPageSize));

        Page<Category> categories = getCategoriesQuery.getCategories(pageRequest);

        return new PageImpl<>(categories.stream()
                .map(CategoryDtoMapper::toDto)
                .collect(Collectors.toList()), pageRequest, categories.getTotalElements());
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
