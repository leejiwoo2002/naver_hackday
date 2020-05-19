package com.hackday.sns_timeline.subscribe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.hackday.sns_timeline.subscribe.domain.entity.SubscribeEs;

public interface SubscribeEsRepository extends ElasticsearchCrudRepository<SubscribeEs, String> {

	Optional<SubscribeEs> findByMemberIdAndSubscribeMemberId(long memberId, long SubscribeMemberId);
	List<SubscribeEs> findByMemberId(long memberId);
	List<SubscribeEs> findBySubscribeMemberEmail(String email);

}
