package com.hackday.sns_timeline.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscribe implements Serializable {

	@EmbeddedId
	private SubscribePK subscribePK;

	private Date regDate;
}
