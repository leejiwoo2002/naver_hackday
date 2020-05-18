package com.hackday.sns_timeline.searchMember.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.hackday.sns_timeline.searchMember.domain.entity.SearchMemberEs;

public interface SearchMemberEsRepository extends ElasticsearchCrudRepository<SearchMemberEs, String> {

	public Page<SearchMemberEs> findByEmail(String email, Pageable pageable);
	public List<SearchMemberEs> findByName(String name);
	public List<SearchMemberEs> findByEmailContainsOrNameContains(String email, String name, Pageable pageable);

}
