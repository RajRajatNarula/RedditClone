package com.example.springredditclone.model;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit 
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@NotBlank(message="Community name is reuired")
	private String name;
	@NotBlank(message="Description is required")
	private String description;
	@OneToMany(fetch = FetchType.LAZY)
	private List<Post> posts;
	private Instant createdDate;
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;
}
