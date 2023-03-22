package com.example.SampleRestApi.service;

import com.example.SampleRestApi.Repository.UserRepository;
import com.example.SampleRestApi.models.SQL.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsernameOrEmail(userNameOrEmail, userNameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Name not Found"));
        Set<GrantedAuthority> authoritySet = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        System.out.println(authoritySet );
        System.out.println(user);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authoritySet);

    }
}
