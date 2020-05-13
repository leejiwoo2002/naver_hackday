package com.hackday.sns_timeline.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.domain.dto.ContentDto;
import com.hackday.sns_timeline.domain.entity.Content;
import com.hackday.sns_timeline.domain.entity.Member;
import com.hackday.sns_timeline.service.ContentService;
import com.hackday.sns_timeline.service.MemberSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentController {

	final private ContentService contentService;
	final private MemberSearchService memberSearchService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView getCreatePage(@ModelAttribute ContentDto contentDto) {
		return new ModelAndView("contentCreate").addObject(CommonConst.CONTENT_DTO, contentDto);
	}

	@RequestMapping(value = "/create/do", method = RequestMethod.POST)
	public String contentCreate(@ModelAttribute(CommonConst.CONTENT_DTO)
	@Valid ContentDto contentDto, @AuthenticationPrincipal User user,
		@RequestParam("file") MultipartFile file) throws Exception {
		log.info(contentDto.getTitle() + " tries to create content");

		String saveName="";
		if(!file.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			saveName = uuid + "_" + file.getOriginalFilename();
			File saveFile = new File(CommonConst.IMAGE_PATH, saveName); // 저장할 폴더 이름, 저장할 파일 이름
			try {
				file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		contentService.contentCreate(contentDto,user,saveName);

		return "redirect:/timeLine";
	}

	@RequestMapping(value = "/readMyContent", method = RequestMethod.GET)
	public ModelAndView readContent(@AuthenticationPrincipal User user,
		@PageableDefault Pageable pageable) {
		log.info("my Content = " + user.getUsername());

		Page<ContentDto> contentDtoList = contentService.findMyContent(user.getUsername(), pageable);

		log.info("contents : " + contentDtoList);
		return new ModelAndView("contentReadMy").addObject(CommonConst.CONTENT_DTO_LIST, contentDtoList);
	}
}
