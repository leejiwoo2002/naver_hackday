package com.hackday.sns_timeline.domain.dto;

import java.sql.Blob;
import java.sql.Timestamp;

import com.hackday.sns_timeline.domain.entity.Content;
import com.hackday.sns_timeline.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {

	private Long content_id;
	private String body;
	private String title;
	private String file_name;
	private Timestamp posting_time;
	private Long member_id;

	static public ContentDto customConverter(Content content){

		return new ContentDto().builder()
			.content_id(content.getContent_id())
			.body(content.getBody())
			.title(content.getTitle())
			.build();
	}

}
