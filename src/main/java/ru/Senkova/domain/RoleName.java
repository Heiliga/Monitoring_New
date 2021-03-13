package ru.Senkova.domain;

public enum RoleName {
    USER_ROLE("user"),
    ADMIN_ROLE("admin");

    private String name;

    RoleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
