package com.microservices.userservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.userservice.dtos.RequestLogin;
import com.microservices.userservice.dtos.ResponseUser;
import com.microservices.userservice.exceptions.IncorrectDetailsException;
import com.microservices.userservice.exceptions.InstantAccountException;
import com.microservices.userservice.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Optional<User> getUser(long id) {
		Optional<User> userbyId = userRepository.findById(id);
		if(userbyId.isEmpty()) {
			throw new ResourceNotFoundException("Not Found id: " + id );
		}
		return userbyId;
	}
	
	public Optional<User> getUserByName(String name) {
		Optional<User> userbyName = userRepository.findByName(name);
		if(userbyName.isEmpty()) {
			throw new ResourceNotFoundException("Not Found name: " + name );
		}
		return userbyName;
	}

    public ResponseUser createUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()) 
        	throw new InstantAccountException("Email already exists");
        
        userRepository.save(user);
        ResponseUser responseUser = new ResponseUser();
        responseUser.setName(user.getName());
        responseUser.setEmail(user.getEmail());
        responseUser.setMessage("Account Successfully Created");
        
        return responseUser;
    }
    
    public String login(RequestLogin requestLogin) {
    	 User user = userRepository.findByEmail(requestLogin.getEmail())
    			 		.orElseThrow(() -> new InstantAccountException("Incorrect login details"));
         if (!requestLogin.getPassword().equals(user.getPassword()))
             throw new IncorrectDetailsException("Incorrect login details");
         if(user.isLoggedIn()) {
        	 user.setLoggedIn(false);
        	 throw new InstantAccountException("You are already logged in. Logged off. Try once again");
         }
         user.setLoggedIn(true);
         userRepository.save(user);
         return "Successfully Logged In";
         
    }
    
    //For testing
    public void deleteAll() {
        userRepository.deleteAll();
    }
    
}

