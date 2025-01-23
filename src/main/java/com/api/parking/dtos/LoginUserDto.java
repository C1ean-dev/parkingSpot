package com.api.parking.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {
    private String username;
    
    private String password;
}
