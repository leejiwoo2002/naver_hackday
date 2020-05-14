package com.hackday.sns_timeline.subscribe.domain.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SubscribeDto {
	private long id;
	private String search;
	private int page;
}
