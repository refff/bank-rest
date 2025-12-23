/*
package com.example.bankcards.util;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.Roles;
import com.example.bankcards.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {

    private final RoleRepository roleRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void createRoles() {
        Role admin = new Role(Roles.ADMIN.getRoleName());
        Role user = new Role(Roles.USER.getRoleName());

        roleRepository.save(admin);
        roleRepository.save(user);
    }
     private void saveRoles(List<Role> dataList){
        roleRepository.saveAll(dataList);
     }
}
*/
