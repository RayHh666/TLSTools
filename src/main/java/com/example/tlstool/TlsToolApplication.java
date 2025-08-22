package com.example.tlstool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication(scanBasePackages = {"com.example.tlstool.controller","com.example.tlstool.service.impl","com.example.tlstool.mapper","com.example.tlstool.Executor","com.example.tlstool.configuration"})
@SpringBootApplication(scanBasePackages = {"com.example.tlstool"})
public class TlsToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(TlsToolApplication.class, args);
    }

}
