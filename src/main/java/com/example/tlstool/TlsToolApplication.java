package com.example.tlstool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.tlstool.controller","com.example.tlstool.service.impl","com.example.tlstool.mapper"})
public class TlsToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(TlsToolApplication.class, args);
    }

}
