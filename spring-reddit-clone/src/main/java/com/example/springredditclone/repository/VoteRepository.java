package com.example.springredditclone.repository;

import com.example.springredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Long>
{

}

