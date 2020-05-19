package com.hackday.sns_timeline.subscribe.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hackday.sns_timeline.common.CommonFunction;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {@Index(columnList="memberID"), @Index(columnList="subscribeMemberId")})
public class Subscribe implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long memberId;

	private long subscribeMemberId;

	private Date regDate;

	static public Subscribe buildSubscribe(long memberId, long subscribeMemberId){
		return Subscribe.builder()
			.memberId(memberId)
			.subscribeMemberId(subscribeMemberId)
			.regDate(CommonFunction.getCurrentDate()).build();
	}
}
