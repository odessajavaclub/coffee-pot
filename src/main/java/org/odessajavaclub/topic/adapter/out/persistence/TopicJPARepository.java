package org.odessajavaclub.topic.adapter.out.persistence;

import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TopicJPARepository extends JpaRepository<TopicEntity, Long> {
    List<TopicEntity> findByType(TopicType type, Pageable pageable);
    List<TopicEntity> findByStatus(TopicStatus status, Pageable pageable);
    List<TopicEntity> findByEventBetween(Date start, Date end, Pageable pageable);
    List<TopicEntity> findByTitleLike(String title, Pageable pageable);
}
