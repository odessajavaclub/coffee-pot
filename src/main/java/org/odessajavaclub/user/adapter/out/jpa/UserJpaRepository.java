package org.odessajavaclub.user.adapter.out.jpa;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends PagingAndSortingRepository<UserEntity, Long> {

  List<UserEntity> findAllByActive(boolean active, Pageable pageable);
}
