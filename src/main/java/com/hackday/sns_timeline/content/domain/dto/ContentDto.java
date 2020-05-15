package com.hackday.sns_timeline.content.domain.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

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
	private Date posting_time;
	private Long member_id;
	private boolean check_delete;

	static public ContentDto customConverter(Content content){

		return new ContentDto().builder()
			.content_id(content.getContent_id())
			.check_delete(content.isCheck_delete())
			.body(content.getBody())
			.title(content.getTitle())
			.file_name(content.getFile_name())
			.posting_time(content.getPosting_time())
			.build();
	}

}
