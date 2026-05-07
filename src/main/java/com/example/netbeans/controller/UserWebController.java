package com.example.netbeans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.netbeans.constant.Role;
import com.example.netbeans.dto.request.UserCreateRequest;
import com.example.netbeans.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserWebController {

	private final UserService userService;

	public UserWebController(UserService userService) {

		this.userService = userService;
	}

	// This maps to http://localhost:8080/users
	@GetMapping
	public String listUsers(Model model) {
		// Fetch all users from your existing service
		model.addAttribute("users", userService.getAllUsers());

		// Returns the path to your thymeleaf template (without .html)
		return "pages/list";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		// Pass an empty DTO to the form for data binding
		model.addAttribute("userDto", new UserCreateRequest());
		// Pass the enum values so the dropdown populates automatically
		model.addAttribute("roles", Role.values());
		return "pages/create";
	}

	// --- 3. Process the Form Submission ---
	@PostMapping("/create")
	public String submitCreateForm(@Valid @ModelAttribute("userDto") UserCreateRequest userDto, BindingResult result,
			Model model) {

		// If validation fails (e.g. blank email), return back to the form page
		if (result.hasErrors()) {
			model.addAttribute("roles", Role.values()); // re-populate dropdown
			return "pages/create";
		}

		try {
			// Attempt to save the user using your existing service
			userService.createUser(userDto);
		} catch (IllegalArgumentException e) {
			// If username/email is duplicate (thrown from Service layer)
			model.addAttribute("roles", Role.values());
			model.addAttribute("errorMessage", e.getMessage());
			return "pages/create";
		}

		// If successful, redirect back to the user list
		return "redirect:/users";
	}
}