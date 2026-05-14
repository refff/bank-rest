package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.Roles;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Roles name;

    public Role() {
    }

    public Role(String name) {
        this.name = Roles.valueOf(name);
    }

}
