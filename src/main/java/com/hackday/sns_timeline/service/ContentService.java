package com.hackday.sns_timeline.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


	public Content contentCreate(ContentDto contentDto) throws Exception {

		LocalDateTime currentDateTime = LocalDateTime.now();


		Content content = Content.builder()
			.title(contentDto.getTitle())
			.body(contentDto.getBody())
			.is_delete(false)
			.posting_time(java.sql.Timestamp.valueOf(currentDateTime.plusHours(9)))
			.build();

		return contentRepository.save(content);
	}


}
