package com.hackday.sns_timeline.sign.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
		Member member = memberRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));

		List<GrantedAuthority> authorities = new ArrayList<>();

		for (String role : member.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return new CustomUser(member.getEmail(), member.getPassword(), authorities, member.getId());
	}

	@Transactional
	public void signUp(MemberDto memberDto) throws Exception {
		if(memberRepository.findByEmail(memberDto.getEmail()).isPresent()){
			throw new Exception("email already exist");
		}

		LocalDateTime currentDateTime = LocalDateTime.now();
		Date date = java.sql.Timestamp.valueOf(currentDateTime);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		Member member = memberRepository.save(Member.builder()
			.email(memberDto.getEmail())
			.name(memberDto.getName())
			.password(passwordEncoder.encode(memberDto.getPassword()))
			.roles(new ArrayList<>(Arrays.asList(Role.MEMBER.getValue())))
			.regDate(date)
			.build());

		subscribeService.addSubscribe(member.getId(), member.getId());
	}
}
