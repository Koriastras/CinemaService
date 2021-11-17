package spring.dao;

import java.util.Optional;
import spring.model.Role;
import spring.model.RoleType;

public interface RoleDao {
    Role add(Role role);

    Optional<Role> getRoleByName(RoleType roleName);
}
