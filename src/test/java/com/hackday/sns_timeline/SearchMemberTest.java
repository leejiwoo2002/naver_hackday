package com.hackday.sns_timeline;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.hackday.sns_timeline.domain.entity.Member;
import com.hackday.sns_timeline.repository.MemberRepository;

@SpringBootTest
public class SearchMemberTest {

	@Autowired
	MemberRepository memberRepository;

	@Test
	public void getMemberListTest(){
		String search = "new";
		Pageable pageable = PageRequest.of(0, 10);
		Page<Member> members = memberRepository.searchMember(search, pageable);

		assertThat(members.getContent().size()).isEqualTo(10);
	}
}
