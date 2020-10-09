package com.example.ecommerce.service;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.ApiResponseDTO;
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

	public ResponseEntity<Object> addUser(UserDTO userDTO) throws Exception {

		Role role = roleRepository.findByName(RoleName.ROLE_USER);
		User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
				passwordEncoder.encode(userDTO.getPassword()), role);
		userRepository.save(user);
		Wallet wallet = new Wallet();
		wallet.setUser(user);
		wallet.setAmount(0f);
		walletRepository.save(wallet);

		return ResponseEntity.created(new URI("/users/" + user.getId())).build();
	}

	public ResponseEntity<ApiResponseDTO> getUser(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		User user = userOptional.get();
		UserDTO userDTO = new UserDTO();
		prepareUserDTO(user, userDTO);
		return ResponseEntity.ok(new ApiResponseDTO(true, userDTO));
	}

	public ResponseEntity<Object> updateUser(Long userId, UserDTO userDTO) {

		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		User user = userOptional.get();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());

		userRepository.save(user);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<Object> deleteUser(Long userId) {

		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		User user = userOptional.get();
		user.setIsActive(false);

		userRepository.save(user);
		return ResponseEntity.noContent().build();
	}

	private void prepareUserDTO(User user, UserDTO userDTO) {
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
	}
}
