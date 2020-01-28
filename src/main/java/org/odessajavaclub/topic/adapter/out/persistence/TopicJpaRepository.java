package org.odessajavaclub.topic.adapter.out.persistence;

import java.util.Date;
import java.util.List;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// TODO: Add find by score
@Repository
public interface TopicJpaRepository extends JpaRepository<TopicEntity, Long> {
  List<TopicEntity> findByType(TopicType type, Pageable pageable);

  List<TopicEntity> findByStatus(TopicStatus status, Pageable pageable);

  List<TopicEntity> findByEventBetween(Date start, Date end, Pageable pageable);

  List<TopicEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
