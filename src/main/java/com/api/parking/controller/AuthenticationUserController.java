package com.api.parking.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parking.dtos.LoginResponseDto;
import com.api.parking.dtos.LoginUserDto;
import com.api.parking.dtos.RegisterUserDto;
import com.api.parking.models.UserModel;
import com.api.parking.service.AuthenticationService;
import com.api.parking.service.JwtService;

import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthenticationUserController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;
    
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationUserController.class);

    public AuthenticationUserController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        if (authenticationService.ExistsByUsername(registerUserDto.getUsername())) {
            logger.error("Erro no cadastro: Usuário já existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Usuário já existe"));
        }
        try{
            UserModel registeredUser = authenticationService.signup(registerUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Erro no cadastro: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erro ao cadastrar usuário"));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            UserModel authenticatedUser = authenticationService.authenticate(loginUserDto);
            LoginResponseDto loginResponsedDto = new LoginResponseDto();
            String jwtToken = jwtService.generateToken(authenticatedUser);
            loginResponsedDto.setToken(jwtToken);
            loginResponsedDto.setExpiresIn(jwtService.getJwtExpiration());
            return ResponseEntity.status(HttpStatus.OK).body(loginResponsedDto);
        }catch (BadCredentialsException  e) {
            logger.error("Erro no login: {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
        }
    }
}