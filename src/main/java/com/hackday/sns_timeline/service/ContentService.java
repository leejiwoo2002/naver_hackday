package com.hackday.sns_timeline.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hackday.sns_timeline.domain.Role;
import com.hackday.sns_timeline.domain.dto.ContentDto;
import com.hackday.sns_timeline.domain.dto.MemberDto;
import com.hackday.sns_timeline.domain.entity.Content;
import com.hackday.sns_timeline.domain.entity.Member;
import com.hackday.sns_timeline.repository.ContentRepository;
import com.hackday.sns_timeline.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ContentService {

	final private ContentRepository contentRepository;
	final private MemberRepository memberRepository;


	public Content contentCreate(ContentDto contentDto, User user,String saveName) throws Exception {

		LocalDateTime currentDateTime = LocalDateTime.now();

		String userName = user.getUsername();

		Member member = memberRepository.findByEmail(userName)
			.orElseThrow(() -> new UsernameNotFoundException(userName));


		Content content = Content.builder()
			.title(contentDto.getTitle())
			.body(contentDto.getBody())
			.is_delete(false)
			.posting_time(java.sql.Timestamp.valueOf(currentDateTime.plusHours(9)))
			.member(member)
			.file_name(saveName)
			.build();

		return contentRepository.save(content);
	}

	public Page<ContentDto> findMyContent(String userName, Pageable pageable) {
		// pageable 에서는 시작페이지가 0 이라서 0번째 페이지를 찾는게 아니면 1을 빼주고 검색
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		// pageable 객체 커스텀
		pageable = PageRequest.of(page, 10);

		Member member = memberRepository.findByEmail(userName)
			.orElseThrow(() -> new UsernameNotFoundException(userName));

		log.info("userName : " + userName);
		log.info("getId : "+ member.getId());

		// 페이지 객체로 찾아옴
		Page<ContentDto> searchMyContent = contentRepository.searchMyContent(member.getId(), pageable).map(ContentDto::customConverter);
		return searchMyContent;
	}


}
