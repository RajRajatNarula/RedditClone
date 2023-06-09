package com.example.springredditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springredditclone.model.Comment;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;

public interface CommentRepository extends JpaRepository<Comment, Long> 
{
	List<Comment> findByPost(Post post);
	List<Comment>  findAllByUser(User user);

}
