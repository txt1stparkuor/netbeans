package com.example.netbeans.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.netbeans.dto.request.UserCreateRequest;
import com.example.netbeans.dto.response.UserResponse;
import com.example.netbeans.entity.User;
import com.example.netbeans.repository.UserRepository;
import com.example.netbeans.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserResponse createUser(UserCreateRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new IllegalArgumentException("Username already exists");
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}
		String encodedPasword = passwordEncoder.encode(request.getPassword());
		User user = new User(request.getUsername(), request.getEmail(), request.getFullName(), encodedPasword,
				request.getRole());
		User savedUser = userRepository.save(user);
		UserResponse userResponse = new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(),
				savedUser.getFullName(), savedUser.getRole());
		return userResponse;
	}

	@Override
	public List<UserResponse> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserResponse> responses = new ArrayList<>();

		for (User user : users) {
			UserResponse dto = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(),
					user.getRole());
			responses.add(dto);
		}

		return responses;
	}
}
