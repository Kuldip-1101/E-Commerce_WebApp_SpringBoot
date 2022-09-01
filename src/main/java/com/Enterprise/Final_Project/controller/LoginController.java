package com.Enterprise.Final_Project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Enterprise.Final_Project.global.GlobalData;
import com.Enterprise.Final_Project.model.Role;
import com.Enterprise.Final_Project.model.User;
import com.Enterprise.Final_Project.repository.RoleRepository;
import com.Enterprise.Final_Project.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	//------------------------Method To get log in page-------------------
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}
	
	//------------------------Method To get register page-------------
	@GetMapping("/register")
	public String registerGet() {
		return "register";
	}
	
	//--------------------Method to register user into database---------------
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException
	{
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		userRepository.save(user);
		request.login(user.getEmail(), password);
		return "redirect:/";
	}
}
