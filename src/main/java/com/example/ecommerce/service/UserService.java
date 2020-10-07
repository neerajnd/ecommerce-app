package com.example.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.RoleName;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Wallet;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.repository.WalletRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	public void addUser(UserDTO userDTO) {
		
		Role role = roleRepository.findByName(RoleName.ROLE_USER);
		User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), role);
		userRepository.save(user);
		Wallet wallet = new Wallet();
		wallet.setUser(user);
		wallet.setAmount(0f);
		walletRepository.save(wallet);
	}
	
	public UserDTO getUser(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if(!userOptional.isPresent()) {
			//not found
		}
		
		User user = userOptional.get();
		UserDTO userDTO = new UserDTO();
		prepareUserDTO(user, userDTO);
		return userDTO;
	}
	
	private void prepareUserDTO(User user, UserDTO userDTO) {
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
	}
}
