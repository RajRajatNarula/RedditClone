package com.example.springredditclone.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="token")
public class VerificationToken 
{
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long Id;
	private String token;
	@OneToOne(fetch = FetchType.LAZY)
	private User user;
	private Instant expiryDate;
}
