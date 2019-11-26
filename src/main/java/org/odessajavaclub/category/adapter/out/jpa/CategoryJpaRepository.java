package org.odessajavaclub.category.adapter.out.jpa;

import org.springframework.data.repository.CrudRepository;

interface CategoryJpaRepository extends CrudRepository<CategoryEntity, Long> {
}
