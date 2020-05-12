package com.hackday.sns_timeline.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackday.sns_timeline.domain.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	@Override
	Optional<Member> findById(Long aLong);

	Optional<Member> findByEmail(String email);

	Optional<Member> findByEmailAndPassword(String email, String password);
}
