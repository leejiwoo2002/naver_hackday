package com.hackday.sns_timeline.content.domain.dto;

import java.sql.Timestamp;

import com.hackday.sns_timeline.content.domain.entity.Content;
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
			.file_name(content.getFile_name())
			.build();
	}

}
