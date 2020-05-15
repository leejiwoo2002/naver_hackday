package com.hackday.sns_timeline.subscribe.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.memberSearch.service.MemberSearchService;
import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.subscribe.domain.dto.SubscribeDto;
import com.hackday.sns_timeline.subscribe.domain.entity.Subscribe;
import com.hackday.sns_timeline.subscribe.service.SubscribeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/subscribe")
@Api(value = "/subscribe", description = "구독 기능 관리")
public class SubscribeController {

	final private SubscribeService subscribeService;
	final private MemberSearchService memberSearchService;

	@ApiOperation(httpMethod = "POST",
		value = "구독 추가 / 삭제 기능, param 으로 오는 Boolean 값으로 추가, 삭제 결정",
		response = String.class,
		nickname="addSubscribe")
	@PostMapping()
	public String addSubscribe(@ModelAttribute("subscribeDto") @Valid SubscribeDto subscribeDto,
		RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUser user) throws Exception {

		if(user == null){
			return "redirect:/";
		}

		if(subscribeDto.getSubscribed()){
			log.info("delete   " + user.getId() + "  " + subscribeDto.getId());
			subscribeService.deleteSubscribe(user.getId(), subscribeDto.getId());
		} else {
			Subscribe subscribe = subscribeService.addSubscribe(user.getId(), subscribeDto.getId());
			if(subscribe.getSubscribePK().getUserId() != user.getId() ||
				subscribe.getSubscribePK().getSubscribeTargetId() != subscribeDto.getId()){
				log.error(subscribe.getSubscribePK().getUserId()  + "   " + user.getId());
				log.error(subscribe.getSubscribePK().getSubscribeTargetId()  + "   " + subscribeDto.getId());
				throw new Exception("subscribe fail");
			}
		}

		Page<MemberDto> memberDtoList = memberSearchService.findMembers(subscribeDto.getSearch(),
			PageRequest.of(subscribeDto.getPage(), 10));

		redirectAttributes.addFlashAttribute("search", subscribeDto.getSearch());
		if(memberDtoList.getContent().size() > 0) {
			memberSearchService.checkSubscribed(memberDtoList, user.getId());
			int start = (int) Math.floor(memberDtoList.getNumber()/10)*10 + 1;
			int last = start + 9 < memberDtoList.getTotalPages() ? start + 9 : memberDtoList.getTotalPages();
			redirectAttributes.addFlashAttribute("start", start);
			redirectAttributes.addFlashAttribute("last", last);
			redirectAttributes.addFlashAttribute(CommonConst.MEMBER_DTO_LIST, memberDtoList);
		}else {
			redirectAttributes.addFlashAttribute("isNull", true);
		}

		return "redirect:/member/search";
	}
}
