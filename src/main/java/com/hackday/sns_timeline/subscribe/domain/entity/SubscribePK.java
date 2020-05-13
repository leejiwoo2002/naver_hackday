package com.hackday.sns_timeline.subscribe.domain.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SubscribePK implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long subscriberId;
	private Long subscribedMemberId;
}
