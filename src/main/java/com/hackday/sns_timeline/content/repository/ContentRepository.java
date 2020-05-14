package com.hackday.sns_timeline.content.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackday.sns_timeline.content.domain.entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

	@Override
	Optional<Content> findById(Long aLong);

	@Query(value = "select * from Content content where content.member_id=:id", nativeQuery = true)
	Page<Content> searchMyContent(Long id, Pageable pageable);
}
