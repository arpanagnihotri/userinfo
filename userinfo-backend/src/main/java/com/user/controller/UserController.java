package com.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repository.UserRepository;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class UserController {
	
	@Autowired
	private UserRepository ur;
	
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return ur.findAll();
	}
	
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		return ur.save(user);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user=ur.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not exist with id:"+id));
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails){
		User user=ur.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not exist with id:"+id));
		
		user.setUsername(userDetails.getUsername());
		user.setPassword(userDetails.getPassword());
		User updatedUser=ur.save(user);
		
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteUser(@PathVariable Long id){
		User user=ur.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not exist with id:"+id));
		ur.delete(user);
		Map<String,Boolean> response=new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
}
