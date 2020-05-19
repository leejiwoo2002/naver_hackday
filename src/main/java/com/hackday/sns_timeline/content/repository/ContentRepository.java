package com.hackday.sns_timeline.content.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackday.sns_timeline.content.domain.entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

	@Override
	Optional<Content> findById(Long aLong);

	@Query(value = "select * from content content where content.member_id=:id and content.check_delete=false order by posting_time DESC", nativeQuery = true)
	Page<Content> searchMyContent(Long id, Pageable pageable);

	@Modifying
	@Transactional
	@Query(value = "update content content set content.check_delete=true where content.content_id=:contentId", nativeQuery = true)
	void removeMyContent(Long contentId);

	@Modifying
	@Transactional
	@Query(value = "update Content content set content.title=:title, content.body=:body where content.content_id=:contentId", nativeQuery = true)
	void updateMyContent(Long contentId, String title, String body);
}
