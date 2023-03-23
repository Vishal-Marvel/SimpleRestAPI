package com.example.SampleRestApi.controller;

import com.example.SampleRestApi.DTO.UserDTO;
import com.example.SampleRestApi.Exceptions.ConstraintException;
import com.example.SampleRestApi.Exceptions.UnAuthorizedException;
import com.example.SampleRestApi.Repository.RoleRepository;
import com.example.SampleRestApi.Repository.UserRepository;
import com.example.SampleRestApi.models.SQL.Role;
import com.example.SampleRestApi.models.SQL.User;
import com.example.SampleRestApi.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.sql.SQLInput;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.SampleRestApi.config.SecurityConfig.passwordEncoder;

@RestController
@SecurityRequirement(name = "Basic Authentication")
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, UserService userService, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping({"", "/", "/users"})
    public List<User> users(){
        return userRepository.findAll();
    }

    @PostMapping("/create")
    public UserDTO create_user(@RequestBody UserDTO userDTO) throws ConstraintException {
        User detached_user = userService.convertDTOToUser(userDTO);
        if (userRepository.findByEmailOrUsername(detached_user.getEmail(), detached_user.getUsername()).isPresent()){
            throw new ConstraintException(
                    "UserName or Email Already Exits"
            );
        }
        User new_user = new User();
        new_user.setName(detached_user.getName());
        new_user.setEmail(detached_user.getEmail());
        new_user.setUsername(detached_user.getUsername());
        new_user.setPassword(passwordEncoder().encode(detached_user.getPassword()));
        new_user.setRoles(Set.of(roleRepository.findRoleByName("ROLE_USER")));
        return userService.convertUserToDTO(userRepository.save(new_user));
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{id}")
    public UserDTO updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Authentication current_user_auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = current_user_auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        org.springframework.security.core.userdetails.User current_user = (org.springframework.security.core.userdetails.User) current_user_auth.getPrincipal();
        if (Objects.equals(current_user.getUsername(), user.getEmail()) || roles.contains("ROLE_ADMIN")) {

            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            if (userRepository.findByEmail(user.getEmail()).isPresent()){
                throw new ConstraintException(
                        "UserName or Email Already Exits"
                );
            }
            return userService.convertUserToDTO(userRepository.save(user));
        }
        else{
            throw new UnAuthorizedException("User unauthorized");
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        return "USER DELETED";
    }



}
