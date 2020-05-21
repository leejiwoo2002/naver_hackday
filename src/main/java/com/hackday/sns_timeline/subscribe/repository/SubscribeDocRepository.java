package com.hackday.sns_timeline.subscribe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.hackday.sns_timeline.subscribe.domain.document.SubscribeDoc;

public interface SubscribeDocRepository extends ElasticsearchCrudRepository<SubscribeDoc, String> {

	Optional<SubscribeDoc> findByMemberIdAndSubscribeMemberId(long memberId, long SubscribeMemberId);
	List<SubscribeDoc> findByMemberId(long memberId);
	List<SubscribeDoc> findBySubscribeMemberEmail(String email);

}
