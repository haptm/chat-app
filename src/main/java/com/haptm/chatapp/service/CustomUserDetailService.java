package com.haptm.chatapp.service;

import com.haptm.chatapp.model.User;
import com.haptm.chatapp.reposiroty.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User with username or email: " + usernameOrEmail + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(), Collections.emptyList());
    }
}
