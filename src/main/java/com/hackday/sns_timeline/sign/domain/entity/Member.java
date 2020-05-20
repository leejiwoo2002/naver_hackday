package com.hackday.sns_timeline.sign.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackday.sns_timeline.common.CommonFunction;
import com.hackday.sns_timeline.sign.domain.Role;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {@Index(columnList="email")})
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Email
	@NotBlank
	@Column(nullable = false, unique = true, length = 100)
	private String email;

	@NotBlank
	@Column(nullable = false, length = 30)
	private String name;

	@NonNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable = false, length = 100)
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	@NonNull
	private Date regDate;

	static public Member buildMember(MemberDto memberDto){
		return Member.builder()
			.email(memberDto.getEmail())
			.name(memberDto.getName())
			.password(CommonFunction.encodingPassword(memberDto.getPassword()))
			.roles(new ArrayList<>(Arrays.asList(Role.MEMBER.getValue())))
			.regDate(CommonFunction.getCurrentDate())
			.build();
	}
}
