package com.example.springredditclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificaionEmail 
{
	private String subject;
	private String recipient;
	private String body;

}
