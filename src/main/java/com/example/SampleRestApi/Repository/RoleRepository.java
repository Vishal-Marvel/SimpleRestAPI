package com.example.SampleRestApi.Repository;

import com.example.SampleRestApi.models.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
