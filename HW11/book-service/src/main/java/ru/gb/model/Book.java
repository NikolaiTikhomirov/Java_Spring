package ru.gb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "books")
@Data
@RequiredArgsConstructor
@Schema(name = "Книга")
public class Book {

  public static long sequence = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(name = "Идентификатор")
  private final long id;
  @Column(name = "name")
  @Schema(name = "Имя")
  private final String name;

  public Book() {
    this(sequence++, "no name");
  }

  public Book(String name) {
    this(sequence++, name);
  }

}
