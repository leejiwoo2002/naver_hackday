package com.hackday.sns_timeline.sign.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.sign.domain.Role;
import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class SignService implements UserDetailsService {

	final private MemberRepository memberRepository;
	final private SubscribeService subscribeService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("try sign in : " + username);

		Member member = memberRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));

		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));

		return new CustomUser(member.getEmail(), member.getPassword(), authorities, member.getId());
	}

	@Transactional
	public void signUp(MemberDto memberDto) throws Exception {
		// email duplicated
		if (checkEmail(memberDto.getEmail())) {
			throw new Exception("Email is duplicated.");
		}

		LocalDateTime currentDateTime = LocalDateTime.now();
		Date date = java.sql.Timestamp.valueOf(currentDateTime.plusHours(9));
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		Member member = Member.builder()
			.email(memberDto.getEmail())
			.name(memberDto.getName())
			.password(passwordEncoder.encode(memberDto.getPassword()))
			.regDate(date)
			.build();

		member = memberRepository.save(member);

		subscribeService.saveSubscribe(member, member);
	}

	private boolean checkEmail(String email) {
		if (memberRepository.findByEmail(email).isPresent())
			return true;
		else
			return false;
	}
}
