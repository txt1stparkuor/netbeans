package com.example.netbeans.service;

import java.util.List;

import com.example.netbeans.dto.request.UserCreateRequest;
import com.example.netbeans.dto.response.UserResponse;

public interface UserService {
	UserResponse createUser(UserCreateRequest request);

	List<UserResponse> getAllUsers();

}
