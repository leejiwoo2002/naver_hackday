package com.hackday.sns_timeline.subscribe.domain.entity;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import com.hackday.sns_timeline.sign.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(indexName = "subscribe", type = "subscribe")
public class SubscribeEs {
	@Id
	private String id;

	private long memberId;

	private String subscribeMemberEmail;

	private String subscribeMemberName;

	private long subscribeMemberId;

	static public SubscribeEs buildSubscribeEs(Member member, Member subscribeMember){
		return SubscribeEs.builder().memberId(member.getId())
			.subscribeMemberEmail(subscribeMember.getEmail()).subscribeMemberName(subscribeMember.getName())
			.subscribeMemberId(subscribeMember.getId()).build();
	}
}
