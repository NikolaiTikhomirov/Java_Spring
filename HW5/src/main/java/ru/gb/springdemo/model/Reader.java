package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "readers")
@Data
@RequiredArgsConstructor
public class Reader {

  public static long sequence = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final long id;
  @Column(name = "name")
  private final String name;

  public Reader() {
    this(sequence++, "no name");
  }

  public Reader(String name) {
    this(sequence++, name);
  }
}
