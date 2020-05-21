package com.hackday.sns_timeline.searchMember;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.searchMember.domain.document.SearchMemberDoc;
import com.hackday.sns_timeline.searchMember.repository.SearchMemberDocRepository;
import com.hackday.sns_timeline.searchMember.service.SearchMemberService;

@SpringBootTest
public class ElasticSearchTest {

	@Autowired
	SearchMemberService searchMemberService;

	@Autowired
	SearchMemberDocRepository searchMemberDocRepository;

	@Test
	@Transactional
	public void saveSearchMemberTest() {
		SearchMemberDoc searchMemberDoc = SearchMemberDoc.builder().email("test@test").name("test").build();
		SearchMemberDoc searchMemberDoc1 = searchMemberService.saveSearchMember(searchMemberDoc);

		assertNotNull(searchMemberDoc1.getId());
		assertThat(searchMemberDoc1.getEmail()).isEqualTo(searchMemberDoc.getEmail());

		searchMemberDocRepository.delete(searchMemberDoc);
		searchMemberDocRepository.delete(searchMemberDoc1);
	}

	@Test
	public void searchSearchMemberTest(){
		String name = "pure";
		String email = "kys";
		int count = 13;
		List<SearchMemberDoc> testData = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			testData.add(searchMemberService.saveSearchMember(
				SearchMemberDoc.builder().email(name+i+"@"+email).name(name+i).build()));
		}

		Page<SearchMemberDoc> searchMemberEsList = searchMemberService.fineSearchMemberByEmailLikeOrNameLike(name);

		assertThat(searchMemberEsList.getContent().size()).isEqualTo(10);

		searchMemberDocRepository.deleteAll(testData);
	}
}
