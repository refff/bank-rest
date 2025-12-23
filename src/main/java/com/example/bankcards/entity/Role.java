package com.example.bankcards.entity;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
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

    public long getId() {
        return id;
    }

    public Role setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name.getRoleName();
    }

    public Role setName(String name) {
        this.name = Roles.valueOf(name);
        return this;
    }
}
