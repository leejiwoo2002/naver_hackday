package com.hackday.sns_timeline.domain.dto;

import com.hackday.sns_timeline.domain.entity.Member;
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

	static public MemberDto customConverter(Member member){

		return new MemberDto().builder().id(member.getId())
			.email(member.getEmail())
			.name(member.getName())
			.build();
	}
}
