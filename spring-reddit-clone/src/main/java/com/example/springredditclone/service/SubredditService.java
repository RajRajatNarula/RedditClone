package com.example.springredditclone.service;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.exception.SpringRedditException;
import com.example.springredditclone.mapper.SubredditMapper;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final SubredditMapper subredditMapper;

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll()
    {
        return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto).collect(Collectors.toList());
    }




    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id)
    {
        Subreddit subreddit=subredditRepository.findById(id).orElseThrow(() ->
                new SpringRedditException("Subreddit not found with this ID- " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

    @Transactional
    public SubredditDto save(SubredditDto subredditDto)
    {
        System.out.println("Entered save method");
        Subreddit subreddit=subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subreddit.setId(subreddit.getId());
        System.out.println("Subreddit DTO: "+subredditDto);
        return subredditDto;
    }


}
