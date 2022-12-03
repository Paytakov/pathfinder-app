package com.example.pathfinder.service;

import com.example.pathfinder.model.dto.UserRegisterDTO;
import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            throw new RuntimeException("passwords do not match");
        }

        Optional<User> byEmail = this.userRepository.findByEmail(userRegisterDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new RuntimeException("email.used");
        }

        User user = new User(
                userRegisterDTO.getUsername(),
                passwordEncoder.encode(userRegisterDTO.getPassword()),
                userRegisterDTO.getEmail(),
                userRegisterDTO.getFullName(),
                userRegisterDTO.getAge()
        );

        this.userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }
}
