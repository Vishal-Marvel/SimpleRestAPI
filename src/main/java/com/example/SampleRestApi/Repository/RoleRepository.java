package com.example.SampleRestApi.Repository;

import com.example.SampleRestApi.models.SQL.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
