package com.hackday.sns_timeline.content.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
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
import com.hackday.sns_timeline.content.domain.dto.ContentDto;
import com.hackday.sns_timeline.content.service.ContentService;
import com.hackday.sns_timeline.memberSearch.service.MemberSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/content")
@Api(value = "/content", description = "컨텐츠 CRUD 기능 담")
public class ContentController {

	final private ContentService contentService;
	final private MemberSearchService memberSearchService;

	@Value("${imagePath.name}")
	private	String filePath;


	@ApiOperation(httpMethod = "GET",
		value = "글 작성 페이지",
		response = ModelAndView.class,
		nickname = "getCreatePage")
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView getCreatePage(@ModelAttribute ContentDto contentDto) {
		return new ModelAndView("layout/contentCreate").addObject(CommonConst.CONTENT_DTO, contentDto);
	}

	@ApiOperation(httpMethod = "POST",
		value = "글 작성 후 메인화면(Timeline) 반환",
		response = String.class,
		nickname = "contentCreate")
	@RequestMapping(value = "/new/do", method = RequestMethod.POST)
	public String contentCreate(@ModelAttribute(CommonConst.CONTENT_DTO)
	@Valid ContentDto contentDto, @AuthenticationPrincipal User user,
		@RequestParam("file") MultipartFile file) throws Exception {
		log.info(contentDto.getTitle() + " tries to create content");

		log.info("filePath : " + filePath);

		String saveName="";
		if(!file.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			saveName = uuid.toString();

			SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd");
			Date today = new Date();
			String folderName = dateFormat.format(today);

			String extension = "."+FilenameUtils.getExtension(file.getOriginalFilename());

			saveName+=extension; // 확장자 추가

			File directory = new File(filePath+"/"+folderName);

			if (!directory.exists()) {
				try{
					directory.mkdir(); //폴더 생성합니다.
				}
				catch(Exception e){
					e.getStackTrace();
				}
			}

			File saveFile = new File(filePath+"/"+folderName, saveName); // 저장할 폴더 이름, 저장할 파일 이름
			try {
				file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		contentService.contentCreate(contentDto,user,saveName);

		return "redirect:/timeLine";
	}

	@ApiOperation(httpMethod = "GET",
		value = "자신의 글 목록 조회 페이지",
		response = ModelAndView.class,
		nickname = "readContent")
	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public ModelAndView readContent(@ModelAttribute(CommonConst.CONTENT_DTO)
	@Valid ContentDto contentDto, @AuthenticationPrincipal User user, @PageableDefault Pageable pageable) {
		log.info("my Content = " + user.getUsername());

		Page<ContentDto> contentDtoList = contentService.findMyContent(user.getUsername(), pageable);

		log.info("contents : " + contentDtoList);
		return new ModelAndView("layout/contentReadMy").addObject(CommonConst.CONTENT_DTO_LIST, contentDtoList);
	}

	@ApiOperation(httpMethod = "POST",
		value = "자신의 글 삭제 함수",
		response = String.class,
		nickname = "deleteContent")
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public String deleteContent(@AuthenticationPrincipal User user, @ModelAttribute(CommonConst.CONTENT_DTO) @Valid ContentDto contentDto ) {
		SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd");
		Date today = new Date();
		contentDto.setPostingTime(today); // date가 string 으로 넘어와서 자동매핑 실패

		contentService.contentRemove(contentDto.getContentId(),user);

		return "redirect:/timeLine";
	}

}
