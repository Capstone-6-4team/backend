package com.example.capstone2.common.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private static final Pattern BEARER_TOKEN_PATTERN = Pattern.compile("[Bb]earer (.*)");
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String jwt = resolveToken(request);
            if(StringUtils.hasText(jwt) && jwtService.validateAccessToken(jwt)) {
                jwtService.createAuthentication(jwt);
            } else {
                log.debug("유효한 JWT 토큰이 없습니다. uri: {}", request.getRequestURI());
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if(!StringUtils.hasText(authorizationHeader)) {
            return null;
        }
        Matcher matcher = BEARER_TOKEN_PATTERN.matcher(authorizationHeader);
        if(matcher.matches()) {
            return matcher.group(1);
        }
        else {
            return null;
        }
    }
}
