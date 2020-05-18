package com.hackday.sns_timeline.searchMember.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.hackday.sns_timeline.searchMember.domain.entity.SearchMember;

public interface SearchMemberRepository extends ElasticsearchCrudRepository<SearchMember, Long> {
	public Page<SearchMember> findByEmail(String email, Pageable pageable);
	public List<SearchMember> findByName(String name);
	public List<SearchMember> findByEmailContains(String email);
}
