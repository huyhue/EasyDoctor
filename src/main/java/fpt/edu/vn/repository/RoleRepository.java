package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fpt.edu.vn.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String roleName);
}
