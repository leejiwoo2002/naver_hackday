package com.hackday.sns_timeline.sign.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.searchMember.domain.document.SearchMemberDoc;
import com.hackday.sns_timeline.searchMember.repository.SearchMemberEsRepository;
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
	final private SearchMemberEsRepository searchMemberEsRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));

		List<GrantedAuthority> authorities = new ArrayList<>();

		for (String role : member.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return new CustomUser(member.getEmail(), member.getPassword(), authorities, member.getId(), member.getName());
	}

	@Transactional
	public Member signUp(MemberDto memberDto) throws Exception {
		if(memberRepository.findByEmail(memberDto.getEmail()).isPresent()){
			throw new Exception("email already exist");
		}

		Member member = memberRepository.save(Member.buildMember(memberDto));

		if(searchMemberEsRepository.findByMemberId(member.getId()).isPresent()){
			throw new Exception("member id already exist");
		}

		searchMemberEsRepository.save(
			SearchMemberDoc.builder()
			.email(member.getEmail())
			.name(member.getName())
			.memberId(member.getId()).build());

		subscribeService.addSubscribe(member.getId(), member.getId());

		return member;
	}
}
