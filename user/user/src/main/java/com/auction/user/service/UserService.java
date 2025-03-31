package com.auction.user.service;

import com.auction.user.entity.User;
import com.auction.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User signup(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        // Basic validation (expand as needed)
        if (user.getUsername().length() < 4 || user.getUsername().length() > 15) {
            throw new RuntimeException("Username must be 4-15 characters");
        }
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }
        return user;
    }

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public String findByUserName(String name) {
		// TODO Auto-generated method stub
		String message = name+" Not Exists";
		Optional<User> user = userRepository.findByUsername(name);
		if(user.isPresent()) {
			message = name+" Already Exists";
		}
		return message;
	}
}
