package com.hackday.sns_timeline.content.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hackday.sns_timeline.content.domain.dto.ContentDto;
import com.hackday.sns_timeline.content.domain.entity.Content;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.content.repository.ContentRepository;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ContentService {

	final private ContentRepository contentRepository;
	final private MemberRepository memberRepository;

	//@Cacheable
	public Content contentCreate(ContentDto contentDto, User user,String saveName) throws Exception {

		LocalDateTime currentDateTime = LocalDateTime.now();

		String userName = user.getUsername();

		Member member = memberRepository.findByEmail(userName)
			.orElseThrow(() -> new UsernameNotFoundException(userName));

		Content content = Content.builder()
			.title(contentDto.getTitle())
			.body(contentDto.getBody())
			.checkDelete(false)
			.postingTime(java.sql.Timestamp.valueOf(currentDateTime))
			.member(member)
			.fileName(saveName)
			.build();

		return contentRepository.save(content);
	}
	//@Cacheable
	public Page<ContentDto> findMyContent(String userName, Pageable pageable) {
		// pageable 에서는 시작페이지가 0 이라서 0번째 페이지를 찾는게 아니면 1을 빼주고 검색
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		// pageable 객체 커스텀
		pageable = PageRequest.of(page, 10);

		Member member = memberRepository.findByEmail(userName)
			.orElseThrow(() -> new UsernameNotFoundException(userName));

		// 페이지 객체로 찾아옴
		Page<ContentDto> searchMyContent = contentRepository.searchMyContent(member.getId(), pageable).map(ContentDto::customConverter);
		return searchMyContent;
	}
	//@Cacheable
	public Page<ContentDto> getMyTimelineContent(List<MemberDto> memberDtoList, Pageable pageable) {
		// pageable 에서는 시작페이지가 0 이라서 0번째 페이지를 찾는게 아니면 1을 빼주고 검색
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		// pageable 객체 커스텀
		pageable = PageRequest.of(page, 10);

		List<Long> idList = new ArrayList<Long>();

		for(int i=0; i<memberDtoList.size(); i++){
			idList.add(memberDtoList.get(i).getId());
		}

		// 페이지 객체로 찾아옴
		Page<ContentDto> searchMyContent = contentRepository.searchMyTimelineContent(idList, pageable).map(ContentDto::customConverter);
		return searchMyContent;
	}
	//@CacheEvict
	public void contentRemove(Long id) {
		contentRepository.removeMyContent(id);

	}

	//@CacheEvict
	public void contentUpdate(Long id, String title, String body) throws Exception {

		contentRepository.updateMyContent(id,title,body);
	}
}
