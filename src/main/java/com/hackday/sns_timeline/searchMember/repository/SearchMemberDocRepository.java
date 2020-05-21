package com.hackday.sns_timeline.searchMember.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.hackday.sns_timeline.searchMember.domain.document.SearchMemberDoc;

public interface SearchMemberDocRepository extends ElasticsearchCrudRepository<SearchMemberDoc, String> {

	List<SearchMemberDoc> findByEmail(String email);
	List<SearchMemberDoc> findByName(String name);
	Optional<SearchMemberDoc> findByMemberId(long memberId);
	Page<SearchMemberDoc> findByEmailContainsOrNameContains(String email, String name, Pageable pageable);

}
