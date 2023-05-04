package com.example.springredditclone.model;

import java.time.Instant;



import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Comment 
{
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long Id;
	@NotEmpty
	private String text;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId" , referencedColumnName = "userId")
	private User user;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="postId", referencedColumnName = "postId")
	private Post post;
	private Instant createdDate;
}
