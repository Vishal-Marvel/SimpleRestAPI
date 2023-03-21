package com.example.SampleRestApi.service;

import com.example.SampleRestApi.DTO.UserDTO;
import com.example.SampleRestApi.models.SQL.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserDTO convertUserToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        );

    }

    public User convertDTOToUser(UserDTO user) {
        return new User(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        );

    }
}
