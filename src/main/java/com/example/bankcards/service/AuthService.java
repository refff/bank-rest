package com.example.bankcards.service;

import com.example.bankcards.dto.JwtAuthResponse;
import com.example.bankcards.dto.UserDTO;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.exception.UserExistException;
import com.example.bankcards.security.JwtService;
import com.example.bankcards.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<?> signUp(UserDTO request){

        userRepository
                .findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new UserExistException();
                });

        Role grantedRole = roleRepository.findById(1).orElseThrow();

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(grantedRole);

        userRepository.save(user);

        return new ResponseEntity<>(Map.of("status", "User created!"), HttpStatus.OK);
    }

    public ResponseEntity<?> getToken() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).get();
        var jwt = jwtService.generateToken(user);

        return new ResponseEntity<>(new JwtAuthResponse(jwt), HttpStatus.OK);
    }
}
