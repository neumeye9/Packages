package com.krista.belt.services;

import java.util.ArrayList;
import java.util.List;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.krista.belt.models.Role;
import com.krista.belt.models.User;
import com.krista.belt.repositories.RoleRepository;
import com.krista.belt.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public void saveWithUserRole(User user) {
		List<Role> userRoles = new ArrayList<>();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRoles.add(roleRepository.findByName("ROLE_USER"));
		user.setRoles(userRoles);
		userRepository.save(user);
	}
	
	public void saveUserWithAdminRole(User user) {
		List<Role> userRoles = new ArrayList<>();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRoles.add(roleRepository.findByName("ROLE_ADMIN"));
		user.setRoles(userRoles);
		userRepository.save(user);
	}
	
	public void update(User user) {
		userRepository.save(user);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User findUserById(Long id) {
		return userRepository.findOne(id);	}
	
	public List<User> allUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	public User getById(Long id) {
		return userRepository.findOne(id);
	}
	
	public void destroy(Long id) {
		userRepository.delete(id);
	}
	
	

}