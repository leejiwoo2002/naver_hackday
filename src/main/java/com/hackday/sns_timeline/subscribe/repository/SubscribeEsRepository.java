package com.hackday.sns_timeline.subscribe.repository;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.hackday.sns_timeline.subscribe.domain.entity.SubscribeEs;

public interface SubscribeEsRepository extends ElasticsearchCrudRepository<SubscribeEs, String> {

	public Optional<SubscribeEs> findByMemberIdAndSubscribeMemberId(long memberId, long SubscribeMemberId);

}
