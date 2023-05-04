package com.example.springredditclone.service;

import com.example.springredditclone.dto.AuthenticationResponse;
import com.example.springredditclone.dto.LoginRequest;
import com.example.springredditclone.dto.RegisterRequest;
import com.example.springredditclone.exception.SpringRedditException;
import com.example.springredditclone.model.NotificaionEmail;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.VerificationToken;
import com.example.springredditclone.repository.UserRepository;
import com.example.springredditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.springredditclone.util.Constants;
import com.example.springredditclone.security.JwtProvider;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest)
    {
        User user=new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodepassword(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token=generateVerificationToken(user);
        String message="Thank you for signing up to Spring Reddit, please click on the below link to activate your account "+
                Constants.ACTIVATION_EMAIL+"/"+token;
        log.info("Message : "+message);
        mailService.sendEmail(new NotificaionEmail("Please Activate your Account",user.getEmail(),message));
        log.info("mail sent to user for verification");

    }

    private String generateVerificationToken(User user)
    {
        String token= UUID.randomUUID().toString();

        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token)
    {
        Optional<VerificationToken> verificationTokenOptional=verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserandEnable(verificationTokenOptional.get());
    }
    @Transactional
    private void fetchUserandEnable(VerificationToken verificationToken)
    {

        String username=verificationToken.getUser().getUsername();
        User user=userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with id - "+username));
        user.setEnabled(true);
        userRepository.save(user);
    }



    private String encodepassword(String passoword)
    {
        return passwordEncoder.encode(passoword);
    }

    public AuthenticationResponse login(LoginRequest loginRequest)
    {
        System.out.println("Entered login in authservice 1");
        System.out.println(loginRequest.getUsername());
        System.out.println(loginRequest.getPassword());
        Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        System.out.println("authenticate");
        System.out.println(authenticate);

        System.out.println("Entered login in authservice 2");
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        System.out.println("Entered login in authservice 3");
        String authenticationToken=jwtProvider.generateToken(authenticate);
        System.out.println("Entered login in authservice 4");
        return new AuthenticationResponse(authenticationToken,loginRequest.getUsername());

    }


    @Transactional(readOnly = true)
    public User getCurrentUser()
    {
        org.springframework.security.core.userdetails.User principal= (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername()).orElseThrow(() ->
                new SpringRedditException("Username not found: "+principal.getUsername()));
    }
}
