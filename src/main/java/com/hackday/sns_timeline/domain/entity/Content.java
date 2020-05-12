
package com.hackday.sns_timeline.domain.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.Temporal;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table
public class Content {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@NotBlank
	private long content_id;

	@NotBlank
	private long member_id;

	@NotBlank
	private String title;

	@NotBlank
	private String body;

	@NotBlank
	private Date posting_time;

	@NotBlank
	private boolean is_delete;


}

