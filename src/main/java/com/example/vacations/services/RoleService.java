package com.example.vacations.services;

import com.example.vacations.dimain.entities.ERole;
import com.example.vacations.dimain.entities.Role;
import org.springframework.stereotype.Service;

import java.util.Set;


public interface RoleService {
    void seedRole();

    Set<Role> getAllRole();

    Role findByRoleName(ERole name);

    Boolean isModerator(String id);
}
