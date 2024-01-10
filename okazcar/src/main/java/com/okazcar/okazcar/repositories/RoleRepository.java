package com.okazcar.okazcar.repositories;

import com.okazcar.okazcar.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRoleName(String roleName);
    Role findRoleByRoleId(int roleId);
}
