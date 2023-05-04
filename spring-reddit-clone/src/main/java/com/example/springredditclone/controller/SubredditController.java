package com.example.springredditclone.controller;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.service.SubredditService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

    private final SubredditService subredditService;
    @GetMapping
    public List<SubredditDto> getAllSubreddits()
    {
        return subredditService.getAll();
    }

    @GetMapping("/{id}")
    public SubredditDto getSubreddit(@PathVariable Long id)
    {
        return subredditService.getSubreddit(id);
    }

    @PostMapping("/sub")
    public ResponseEntity<SubredditDto> postSubreddit(@RequestBody @Valid SubredditDto subredditDto)
    {
        System.out.println("Entered subreddit post controller");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.save(subredditDto));
    }

}
