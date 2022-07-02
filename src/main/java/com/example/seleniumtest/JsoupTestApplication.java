package com.example.seleniumtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JsoupTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(JsoupTestApplication.class, args).getBean(JsoupTestApplication.class);
    }
}
