package com.example.SampleRestApi.DTO;

import com.example.SampleRestApi.models.SQL.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    private Set<Role> roles = new HashSet<>();
}
