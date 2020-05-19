package com.hackday.sns_timeline.searchMember;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.searchMember.domain.entity.SearchMemberEs;
import com.hackday.sns_timeline.searchMember.repository.SearchMemberEsRepository;
import com.hackday.sns_timeline.searchMember.service.SearchMemberService;

@SpringBootTest
public class ElasticSearchTest {

	@Autowired
	SearchMemberService searchMemberService;

	@Autowired
	SearchMemberEsRepository searchMemberEsRepository;

	@Test
	@Transactional
	public void saveSearchMemberTest() {
		SearchMemberEs searchMemberEs = SearchMemberEs.builder().id(1).email("test@test").name("test").build();
		SearchMemberEs searchMemberEs1 = searchMemberService.saveSearchMember(searchMemberEs);

		assertNotNull(searchMemberEs1.getId());
		assertThat(searchMemberEs1.getEmail()).isEqualTo(searchMemberEs.getEmail());
	}

	@Test
	public void searchSearchMemberTest(){
		String name = "pure";
		String email = "kys";
		int count = 13;
		List<SearchMemberEs> testData = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			testData.add(searchMemberService.saveSearchMember(
				SearchMemberEs.builder().id(i).email(name+i+"@"+email).name(name+i).build()));
		}

		List<SearchMemberEs> searchMemberEsList = searchMemberService.fineSearchMemberByEmailLikeOrNameLike(name);

		assertThat(searchMemberEsList.size()).isEqualTo(10);

		searchMemberEsRepository.deleteAll(testData);
	}
}
