package com.hackday.sns_timeline.searchMember.domain.entity;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(indexName = "sns", type = "member")
public class SearchMember {
	@Id
	private long id;
	private String email;
	private String name;
}
