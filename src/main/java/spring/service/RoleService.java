package spring.service;

import spring.model.Role;
import spring.model.RoleType;

public interface RoleService {
    void add(Role role);

    Role getRoleByName(RoleType roleName);
}
