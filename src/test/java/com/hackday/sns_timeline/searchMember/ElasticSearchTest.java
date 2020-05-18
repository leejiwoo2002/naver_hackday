package com.hackday.sns_timeline.searchMember;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.searchMember.domain.entity.SearchMember;
import com.hackday.sns_timeline.searchMember.service.SearchMemberService;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;

@SpringBootTest
public class ElasticSearchTest {

	@Autowired
	SearchMemberService searchMemberService;

	@Test
	@Transactional
	public void saveSearchMemberTest() {
		SearchMember searchMember = SearchMember.builder().id(1).email("test@test").name("test").build();
		SearchMember searchMember1 = searchMemberService.saveSearchMember(searchMember);

		assertNotNull(searchMember1.getId());
		assertThat(searchMember1.getEmail()).isEqualTo(searchMember.getEmail());
	}

	@Test
	@Transactional
	public void searchSearchMemberTest(){
		String name = "test";
		int count = 13;
		for (int i = 0; i < count; i++) {
			searchMemberService.saveSearchMember(SearchMember.builder().id(i).email(name+i+"@test").name(name+i).build());
		}
		List<SearchMember> searchMemberList = searchMemberService.fineSearchMemberByEmailLike(name);

		assertThat(searchMemberList.size()).isEqualTo(count);
	}
}
