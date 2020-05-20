package com.hackday.sns_timeline.common.commonEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PAGE {

	INDEX("layout/index"),
	SIGN_UP("layout/signUp"),
	TIME_LINE("layout/timeLine"),
	PROFILE("layout/profile"),
	ERROR("errors/error"),
	SEARCH_MEMBER("layout/searchMember");

	private final String page;
}
