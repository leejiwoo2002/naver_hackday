
package com.hackday.sns_timeline.content.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@Table
public class Content {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long content_id;

	@ManyToOne
	private Member member;

	@NotBlank
	private String title;

	private String file_name;

	@NotBlank
	private String body;

	@NotNull
	private Date posting_time;

	@NotNull
	private boolean check_delete;
}
