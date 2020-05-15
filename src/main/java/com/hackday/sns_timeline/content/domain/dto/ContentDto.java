package com.hackday.sns_timeline.content.domain.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import com.hackday.sns_timeline.content.domain.entity.Content;
import com.hackday.sns_timeline.sign.domain.entity.Member;
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

	private Long contentId;
	private String body;
	private String title;
	private String fileName;
	private Date postingTime;
	private Member member;
	private boolean checkDelete;

	static public ContentDto customConverter(Content content){

		return new ContentDto().builder()
			.contentId(content.getContentId())
			.checkDelete(content.isCheckDelete())
			.body(content.getBody())
			.title(content.getTitle())
			.fileName(content.getFileName())
			.postingTime(content.getPostingTime())
			.member(content.getMember())
			.build();
	}

}
