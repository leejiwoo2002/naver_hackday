package com.hackday.sns_timeline.profile.controller;

import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.sns_timeline.common.commonEnum.ATTRIBUTE;
import com.hackday.sns_timeline.common.commonEnum.PAGE;
import com.hackday.sns_timeline.profile.service.ProfileService;
import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
@Api(value = "/profile", description = "프로필 관리")
public class ProfileController {

	private final ProfileService profileService;

	@ApiOperation(
		httpMethod = "GET",
		value = "프로필 페이지와 프로필 정보 반환",
		response = ModelAndView.class,
		nickname="getProfilePage"
	)
	@GetMapping
	public ModelAndView getProfilePage(@AuthenticationPrincipal CustomUser user) {
		if(Objects.isNull(user)){
			return new ModelAndView(PAGE.INDEX.getPage());
		}
		Member member = profileService.getMemberProfile(user.getId());

		return new ModelAndView(PAGE.PROFILE.getPage())
			.addObject(ATTRIBUTE.MEMBER_DTO.getName(), MemberDto.memberConverter(member))
			.addObject(ATTRIBUTE.SUBSCRIBE_LIST.getName(), profileService.getSubscribeMember(member));
	}
}
