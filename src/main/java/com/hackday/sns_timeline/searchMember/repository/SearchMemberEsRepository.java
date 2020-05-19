package com.hackday.sns_timeline.searchMember.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.hackday.sns_timeline.searchMember.domain.entity.SearchMemberEs;

public interface SearchMemberEsRepository extends ElasticsearchCrudRepository<SearchMemberEs, String> {

	List<SearchMemberEs> findByEmail(String email);
	List<SearchMemberEs> findByName(String name);
	List<SearchMemberEs> findByEmailContainsOrNameContains(String email, String name, Pageable pageable);

}
