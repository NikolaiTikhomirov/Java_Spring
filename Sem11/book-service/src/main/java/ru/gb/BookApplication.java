package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.graphql.servlet.GraphQlWebMvcAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class BookApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookApplication.class, args);
  }

}