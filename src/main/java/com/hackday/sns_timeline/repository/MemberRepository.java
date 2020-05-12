package com.hackday.sns_timeline.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackday.sns_timeline.domain.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	@Override
	Optional<Member> findById(Long aLong);

	Optional<Member> findByEmail(String email);

	Optional<Member> findByEmailAndPassword(String email, String password);

	@Query(value = "select * from Member member where member.email like %:search% or member.name like %:search%", nativeQuery = true)
	Page<Member> searchMember(@Param("search") String search, Pageable pageable);
}
