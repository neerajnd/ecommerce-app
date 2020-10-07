package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(RoleName roleName);
}