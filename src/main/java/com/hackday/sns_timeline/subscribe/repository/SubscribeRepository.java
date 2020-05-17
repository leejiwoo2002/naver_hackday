package com.hackday.sns_timeline.subscribe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.subscribe.domain.entity.Subscribe;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

	@Query(value = "select subscribe.subscribeMember from Subscribe subscribe where subscribe.member = :member")
	List<Member> findSubscribeIdByMember(@Param("member") Member member);

	List<Subscribe> findByMember(@Param("member") Member member);

	Optional<Subscribe> findByMemberAndSubscribeMember(Member member, Member subscribeMember);
}
