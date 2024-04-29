package com.microservices.userservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.userservice.dtos.RequestLogin;
import com.microservices.userservice.dtos.ResponseApi;
import com.microservices.userservice.exceptions.IncorrectDetailsException;
import com.microservices.userservice.exceptions.InstantAccountException;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users/id/{id}")
	public Optional<User> retrieveUser(@PathVariable long id) {
		Optional<User> user = userService.getUser(id);
		return user;
	}
	
	@GetMapping("/users/{name}")
	public Optional<User> retrieveUserByName(@PathVariable String name) {
		Optional<User> user = userService.getUserByName(name);
		return user;
	}
	
	@PostMapping("users/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
		
		//String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        User createUser = new User(user.getName(), user.getEmail(), user.getPassword(), false);
        
        // Call user service to create user and handle potential exceptions
	    try {
	    	return new ResponseEntity<>(userService.createUser(createUser), HttpStatus.CREATED);
	    }
	    catch(InstantAccountException exception) {
	    	return (ResponseEntity<?>) ResponseEntity.badRequest();
	    } 
	}
	
	@PostMapping("users/login")
    public ResponseEntity<?> login(@RequestBody RequestLogin requestLogin) {
        try {
            return new ResponseEntity<>(userService.login(requestLogin),HttpStatus.FOUND);
        } catch (InstantAccountException  | IncorrectDetailsException error) {
            return new ResponseEntity<>(new ResponseApi(false, error.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }    
	
}
