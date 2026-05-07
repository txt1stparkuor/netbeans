package com.example.netbeans.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.netbeans.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}
