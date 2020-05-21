package com.hackday.sns_timeline.common.commonEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum REDIRECT {

	INDEX("redirect:/"),
	SEARCH_MEMBER("redirect:/search/member"),
	TIME_LINE("redirect:/timeLine");

	private final String redirectUrl;
}
