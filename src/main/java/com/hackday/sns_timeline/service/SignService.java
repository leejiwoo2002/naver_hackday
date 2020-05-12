package com.hackday.sns_timeline.service;

import java.util.ArrayList;
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
import com.hackday.sns_timeline.domain.dto.MemberDto;
import com.hackday.sns_timeline.domain.entity.Member;
import com.hackday.sns_timeline.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class SignService implements UserDetailsService {

	final private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("try sign in : " + username);

		Member member = memberRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));

		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));

		return new User(member.getEmail(), member.getPassword(), authorities);
	}


	public Member signUp(MemberDto memberDto) throws Exception {
		// email duplicated
		if (checkEmail(memberDto.getEmail())) {
			throw new Exception("Email is duplicated.");
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		Member member = Member.builder()
			.email(memberDto.getEmail())
			.name(memberDto.getName())
			.password(passwordEncoder.encode(memberDto.getPassword()))
			.build();

		return memberRepository.save(member);
	}

	private boolean checkEmail(String email) {
		if (memberRepository.findByEmail(email).isPresent())
			return true;
		else
			return false;
	}

	private Member checkPassword(String email, String password) throws Exception {
		Member member = memberRepository.findByEmailAndPassword(email, password)
			.orElseThrow(() -> new Exception("Check your password"));
		return member;
	}
}
