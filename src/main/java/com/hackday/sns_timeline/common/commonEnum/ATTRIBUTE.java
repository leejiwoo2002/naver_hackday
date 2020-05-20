package com.hackday.sns_timeline.common.commonEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ATTRIBUTE {

	MEMBER_DTO("memberDto"),
	MEMBER_DTO_LIST("memberDtoList"),
	CONTENT("content"),
	CONTENT_DTO("contentDto"),
	CONTENT_DTO_LIST("contentDtoList"),
	MEMBERS("members"),
	SUBSCRIBE_DTO("subscribeDto"),
	SUBSCRIBE_LIST("subscribeList"),
	ERROR("error"),
	IS_NULL("isNull"),
	SEARCH("search"),
	START("start"),
	LAST("last");

	private final String name;
}
