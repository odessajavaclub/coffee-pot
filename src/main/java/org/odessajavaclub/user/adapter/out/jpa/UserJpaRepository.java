package org.odessajavaclub.user.adapter.out.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpaRepository extends PagingAndSortingRepository<UserEntity, Long> {

    List<UserEntity> findAllByActive(boolean active);
}
