package com.hackday.sns_timeline.subscribe.domain.document;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import com.hackday.sns_timeline.searchMember.domain.document.SearchMemberDoc;
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
public class SubscribeDoc {
	@Id
	private String id;

	private long memberId;

	private String subscribeMemberEmail;

	private String subscribeMemberName;

	private long subscribeMemberId;

	static public SubscribeDoc buildSubscribeEs(SearchMemberDoc member, SearchMemberDoc subscribeMember){
		return SubscribeDoc.builder().memberId(member.getMemberId())
			.subscribeMemberEmail(subscribeMember.getEmail()).subscribeMemberName(subscribeMember.getName())
			.subscribeMemberId(subscribeMember.getMemberId()).build();
	}
}
