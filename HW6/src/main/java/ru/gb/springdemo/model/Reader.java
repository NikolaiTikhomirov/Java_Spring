package ru.gb.springdemo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "readers")
@Data
@RequiredArgsConstructor
@Schema(name = "Читатель")
public class Reader {

  public static long sequence = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(name = "Идентификатор")
  private final long id;
  @Column(name = "name")
  @Schema(name = "Имя читателя")
  private final String name;

  public Reader() {
    this(sequence++, "no name");
  }

  public Reader(String name) {
    this(sequence++, name);
  }
}
