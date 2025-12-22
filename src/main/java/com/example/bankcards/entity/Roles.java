package com.example.bankcards.entity;

public enum Roles {
    USER("USER"), ADMIN("ADMIN");

    private String roleName;

    Roles(String role) {
        this.roleName = role;
    }

    public String getRoleName() {
        return roleName;
    }
}
