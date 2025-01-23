package com.api.parking_control.DatabaseConnectionTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testConnection() {
        // Execute uma simples consulta para verificar a conexão
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        
        // Verifica se o resultado da consulta não é nulo
        assertNotNull(result, "A conexão com o banco de dados falhou");
    }
}
