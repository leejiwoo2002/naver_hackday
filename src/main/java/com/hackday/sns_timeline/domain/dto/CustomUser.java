package com.hackday.sns_timeline.domain.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUser extends User {

	private long id;

	public CustomUser(String username, String password,
		Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CustomUser(String username, String password,
		Collection<? extends GrantedAuthority> authorities, long id) {
		super(username, password, authorities);
		this.id = id;
	}
}
