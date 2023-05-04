package com.example.springredditclone.model;

import java.time.Instant;

import org.hibernate.annotations.ManyToAny;

import io.micrometer.common.lang.Nullable;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long postId;
	@NotBlank(message = "Post name cannot be empty or null")
	private String postName;
	@Nullable
	private String url;
	@Nullable
	@Lob
	private String decription;
	private Integer voteCount;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;
	private Instant createDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id",referencedColumnName = "id")
	private Subreddit subreddit;
	

}
