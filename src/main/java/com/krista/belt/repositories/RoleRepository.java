package com.krista.belt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.krista.belt.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	List<Role> findAll();
	Role findByName(String name);
	
	@Query(value="SELECT id from roles WHERE name='ROLE_ADMIN'", nativeQuery=true)
	List<Role> findAdminRole();

}
