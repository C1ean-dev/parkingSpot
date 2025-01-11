package com.api.parking.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.format.DateTimeFormatter;

@Configuration
public class ParkingSpotFormatDateConfig {
    @Bean
    @Primary
    ObjectMapper dateConfig(){
        String dateTimeFormat = "'yyyy-MM-dd' 'T' 'HH:mm:ss ' 'Z' ";
        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat));
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(localDateTimeSerializer);
        return new ObjectMapper().registerModule(module);
    }
}
