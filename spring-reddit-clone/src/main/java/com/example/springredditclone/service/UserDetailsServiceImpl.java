package com.example.springredditclone.service;

import com.example.springredditclone.exception.SpringRedditException;
import com.example.springredditclone.model.User;
import com.example.springredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import static java.util.Collections.singletonList;
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username){
        Optional<User> userOptional= userRepository.findByUsername(username);
        System.out.println("UserOptional : "+userOptional);
        User user=userOptional.orElseThrow(() -> new SpringRedditException("No User found this username "+username));
        System.out.println("IN User service Impl");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.isEnabled());

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.isEnabled(),true,true,true,getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role)
    {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
