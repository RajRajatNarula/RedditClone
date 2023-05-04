package com.example.springredditclone.security;

import com.example.springredditclone.exception.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            System.out.println("Entered Init");
            keyStore = KeyStore.getInstance("JKS");
            System.out.println("Keystore : "+keyStore);
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            System.out.println("resourceAsstream : "+resourceAsStream);
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringRedditException("Exception occured while loading keystore");
        }

    }

    public String generateToken(Authentication authentication) {
        System.out.println("authentication.getPrincipal(): "+authentication.getPrincipal());
        org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
        System.out.println("Toekn Generated: "+Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact());
        return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringRedditException("Exception occured while retrieving public key from keystore");
        }
    }

    public boolean validateToken(String jwt)
    {
        parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublickey()
    {
        try
        {
            return keyStore.getCertificate("springblog").getPublicKey();
        }
        catch (KeyStoreException e)
        {
            throw  new SpringRedditException("Exeption occured while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJWT(String token)
    {
        Claims claims=parser().setSigningKey(getPublickey()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}