package com.hackday.sns_timeline.sign.domain.dto;

import com.hackday.sns_timeline.searchMember.domain.entity.SearchMemberEs;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.subscribe.domain.entity.SubscribeEs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberDto {
	private long id;
	private String email;
	private String name;
	private String password;
	private boolean isSubscribed;

	static public MemberDto memberConverter(Member member){
		return new MemberDto().builder().id(member.getId())
			.email(member.getEmail())
			.name(member.getName())
			.build();
	}

	static public MemberDto subscribeEsConverter(SubscribeEs subscribeEs){
		return new MemberDto().builder().email(subscribeEs.getSubscribeMemberEmail())
			.name(subscribeEs.getSubscribeMemberName()).id(subscribeEs.getSubscribeMemberId())
			.build();
	}
}
