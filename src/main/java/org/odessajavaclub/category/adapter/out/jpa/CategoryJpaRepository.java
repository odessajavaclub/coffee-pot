package org.odessajavaclub.category.adapter.out.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

interface CategoryJpaRepository extends PagingAndSortingRepository<CategoryEntity, Long> {
}
