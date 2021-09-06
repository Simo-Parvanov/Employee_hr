package com.example.vacations.services.impl;

import com.example.vacations.dimain.entities.ERole;
import com.example.vacations.dimain.entities.Role;
import com.example.vacations.repository.RoleRepository;
import com.example.vacations.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRole() {
        if (roleRepository.count() == 0) {
            for (ERole eRole : ERole.values()) {
                Role role = new Role();
                role.setName(eRole);
                roleRepository.saveAndFlush(role);
            }
        }
    }

    @Override
    public Set<Role> getAllRole() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public Role findByRoleName(ERole name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public Boolean isModerator(String id) {

        return null;
    }
}
