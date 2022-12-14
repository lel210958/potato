package com.lel.potato.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.lel.potato"})
public class PotatoWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PotatoWebApplication.class, args);
    }

}
