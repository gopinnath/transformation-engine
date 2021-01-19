package com.gopinnath.transformer.example.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gopinnath.transformer.example")
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

}