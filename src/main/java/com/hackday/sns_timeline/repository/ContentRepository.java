package com.hackday.sns_timeline.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackday.sns_timeline.domain.entity.Content;
import com.hackday.sns_timeline.domain.entity.Member;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

/*	@Override
	Optional<Content> findById(Long aLong);

	Optional<Content> findByTitle(String title);

	Optional<Content> findByEmailAndPassword(String email, String password);*/
}
