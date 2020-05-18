package com.hackday.sns_timeline.searchMember.domain.entity;

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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(indexName = "member", type = "member")
public class SearchMemberEs {
	@Id
	private String id;
	private long memberId;
	private String email;
	private String name;

	static public SearchMemberEs buildSearchMemberEs(Member member){
		return SearchMemberEs.builder().memberId(member.getId())
			.email(member.getEmail()).name(member.getName()).build();
	}
}
