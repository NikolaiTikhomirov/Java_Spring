package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "books")
@Data
@RequiredArgsConstructor
public class Book {

  public static long sequence = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final long id;
  @Column(name = "name")
  private final String name;

  public Book() {
    this(sequence++, "no name");
  }

  public Book(String name) {
    this(sequence++, name);
  }

}
