package com.example.capstone2.user.service;

import com.example.capstone2.user.dao.UserRepository;
import com.example.capstone2.user.dto.RegisterRequest;
import com.example.capstone2.user.entity.User;
import com.example.capstone2.user.entity.infodetails.AvailableLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> createUserDetails(user))
                .orElseThrow(() -> new UsernameNotFoundException("[" + email + "] 존재하지 않는 유저 입니다."));
    }

    private UserDetails createUserDetails(User user) {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getUserType().toString());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }

    @Transactional(readOnly = true)
    public boolean isExistEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Transactional
    public void create(RegisterRequest registerRequest) {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User user = User.from(registerRequest);
        user.getAvailableLanguages().addAll(registerRequest.getAvailableLanguages()
                .stream()
                .map(l -> AvailableLanguage.of(l, user))
                .collect(Collectors.toList()));

        userRepository.save(user);
    }
}
