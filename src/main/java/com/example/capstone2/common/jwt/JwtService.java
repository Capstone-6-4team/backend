package com.example.capstone2.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.refresh-secret-key}")
    private String REFRESH_SECRET_KEY;

    private static final String AUTHORITIES_KEY = "auth";

    private Key key;

    private final long ACCESS_TOKEN_EXPIRATION_MS = 1800000; // 30분
    private final long REFRESH_TOKEN_EXPIRATION_MS = 1209600000; // 14일

    @PostConstruct
    private void setKey() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

//    public String generateAccessToken(String email) {
//        return generateToken(email, SECRET_KEY, ACCESS_TOKEN_EXPIRATION_MS);
//    }
//
//    public String generateRefreshToken(String email) {
//        return generateToken(email, REFRESH_SECRET_KEY, REFRESH_TOKEN_EXPIRATION_MS);
//    }

    public String getEmailFromAccessJwt(String jwt) {
        return getEmailFromJwt(jwt, SECRET_KEY);
    }

    public String getEmailFromRefreshJwt(String jwt) {
        return getEmailFromJwt(jwt, REFRESH_SECRET_KEY);
    }

    public String getEmailFromJwt(String jwt, String secret) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build();
        Claims claims = parser.parseClaimsJws(jwt)
                .getBody();

        return claims.getSubject();
    }

    public String generateToken(Authentication authentication) {
        System.out.println("JwtService.generateToken");
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        System.out.println("authorities = " + authorities);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateAccessToken(String jwt) {
        return validateToken(jwt, SECRET_KEY);
    }

    public boolean validateRefreshToken(String jwt) {
        return validateToken(jwt, REFRESH_SECRET_KEY);
    }

    private boolean validateToken(String jwt, String secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);
            return true;
        } catch(ExpiredJwtException e) {
            log.error("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰입니다.");
        } catch (MalformedJwtException e) {
            log.error("잘못된 형식의 토큰입니다.");
        } catch (SignatureException e) {
            log.error("잘못된 Signature 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("claimJws 문자열이 비어있습니다.");
        }
        return false;
    }

    public Authentication getAuthentication(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public void createAuthentication(String jwt) {
        Authentication authentication = getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
