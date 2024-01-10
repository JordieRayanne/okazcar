package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.Role;
import com.okazcar.okazcar.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findOne(int roleId) {
        return roleRepository.findRoleByRoleId(roleId);
    }

    public Role insert(Role role) {
        return roleRepository.save(role);
    }

    public Role update(Role role) {
        return roleRepository.save(role);
    }

    public int delete(int roleId) {
         roleRepository.deleteById(roleId);
         return roleId;
    }
}
