package com.example.SampleRestApi.DTO;

import com.example.SampleRestApi.models.User.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    private Set<Role> roles;

    public UserDTO(Long id, String name, String email, String username, String password, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
