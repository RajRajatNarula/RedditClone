package com.example.springredditclone.security;

import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        System.out.println("Entered jwtAuthenticationfilter");
        String jwt=getJwtFromRequest(request);
        System.out.println("JWT: "+jwt);
        if(StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt))
        {
            System.out.println("Entered first IF");
            if(jwtProvider.validateToken(jwt))
            {
                System.out.println("Entered second IF");
                System.out.println("Entered IF condition");
                String username = jwtProvider.getUsernameFromJWT(jwt);
                System.out.println("Username: " + username);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("userDetails: " + userDetails);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }

    private String getJwtFromRequest(HttpServletRequest request)
    {
        System.out.println("Entered getJWTfromRequest");
        System.out.println("Request is: "+request);
        String bearerToken=request.getHeader("Authorization");
        System.out.println("BearerToken: "+bearerToken);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
