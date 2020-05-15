package com.hackday.sns_timeline.profile.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.profile.service.ProfileService;
import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
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

	final private ProfileService profileService;

	@ApiOperation(
		httpMethod = "GET",
		value = "프로필 페이지와 프로필 정보 반환",
		response = ModelAndView.class,
		nickname="getProfilePage"
	)
	@GetMapping
	public ModelAndView getProfilePage(@AuthenticationPrincipal CustomUser user) throws Exception {
		if(user == null){
			return new ModelAndView(CommonConst.TIME_LINE);
		}
		ModelAndView modelAndView = new ModelAndView(CommonConst.PROFILE);

		Member member = profileService.getMemberProfile(user.getId());
		MemberDto memberDto = MemberDto.customConverter(member);

		List<MemberDto> subscribeMember = profileService.getSubscribeMember(member);

		log.info("구독자 수 = " + subscribeMember.size());

		modelAndView.addObject(CommonConst.MEMBER_DTO, memberDto);
		modelAndView.addObject(CommonConst.SUBSCRIBE_LIST, subscribeMember);

		return modelAndView;
	}
}
