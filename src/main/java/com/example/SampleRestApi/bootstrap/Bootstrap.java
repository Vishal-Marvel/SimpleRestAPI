package com.example.SampleRestApi.bootstrap;

import com.example.SampleRestApi.Repository.RoleRepository;
import com.example.SampleRestApi.Repository.UserRepository;
import com.example.SampleRestApi.models.User.Role;
import com.example.SampleRestApi.models.User.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

//@Component
public class Bootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Bootstrap(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role user = new Role(1L, "ROLE_USER");
        Role admin = new Role(2L, "ROLE_ADMIN");
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(user);
        roles.add(admin);
        roleRepository.saveAll(roles);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User admin_user = new User(1L, "Admin", "admin@gmail.com", "admin", passwordEncoder.encode("password"), Set.of(admin, user));
        userRepository.save(admin_user);

    }
}
