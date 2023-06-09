package com.example.springredditclone.repository;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>
{
	List<Post> findAllBySubreddit(Subreddit subreddit);
	List<Post> findByUser(User user);
}
