package com.example.SampleRestApi.controller;

import com.example.SampleRestApi.DTO.UserDTO;
import com.example.SampleRestApi.Repository.RoleRepository;
import com.example.SampleRestApi.Repository.UserRepository;
import com.example.SampleRestApi.models.SQL.Role;
import com.example.SampleRestApi.models.SQL.User;
import com.example.SampleRestApi.service.UserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.SampleRestApi.config.SecurityConfig.passwordEncoder;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, UserService userService, RoleRepository roleRepository, List<Role> roles) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<User> users(){
        return userRepository.findAll();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO create_user(@RequestBody UserDTO userDTO){
        User detached_user = userService.convertDTOToUser(userDTO);
        User new_user = new User();
        new_user.setName(detached_user.getName());
        new_user.setEmail(detached_user.getEmail());
        new_user.setUsername(detached_user.getUsername());
        new_user.setPassword(passwordEncoder().encode(detached_user.getPassword()));
        new_user.setRoles(Set.of(roleRepository.findRoleByName("ROLE_USER")));
        return userService.convertUserToDTO(userRepository.save(new_user));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/update/{id}")
    public UserDTO updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return userService.convertUserToDTO(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        return "USER DELETED";
    }



}
