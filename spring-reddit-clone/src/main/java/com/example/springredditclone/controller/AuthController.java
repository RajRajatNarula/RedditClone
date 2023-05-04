package com.example.springredditclone.controller;

import com.example.springredditclone.dto.AuthenticationResponse;
import com.example.springredditclone.dto.LoginRequest;
import com.example.springredditclone.dto.RegisterRequest;
import com.example.springredditclone.exception.SpringRedditException;
import com.example.springredditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController
{

    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest)
    {
        authService.signup(registerRequest);
        return new ResponseEntity("User registered successfully",OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest)
    {
        System.out.println("Entered controller");
        return authService.login(loginRequest);

    }
    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token)
    {
        authService.verifyAccount(token);
        return new ResponseEntity<>("The Account is verified Sucessfully",OK);
    }

}

