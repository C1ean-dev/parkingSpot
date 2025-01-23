package com.api.parking.models;

import java.util.List;

public enum UserRole {
    
    ADMIN(List.of("ROLE_ADMIN")),
    USER(List.of("ROLE_USER"));

    private List<String> authorities;

    UserRole(List<String> authorities){
        this.authorities = authorities;
    }

    public List<String> getAuthorities(){
        return authorities;
    }
}
