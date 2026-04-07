package com.example.bankcards.service;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.Roles;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminService(UserRepository userRepository,
                        RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public ResponseEntity<?> getAdminAccess() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).get();
        Role admin = roleRepository.findById(7).get();

        user.setRoles(admin);

        userRepository.save(user);

        return new ResponseEntity<>(Map.of(
               "subject", username,
               "status", Roles.ADMIN.getRoleName()
               ), HttpStatus.OK);
    }

}
