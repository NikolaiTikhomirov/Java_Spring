package ru.gb.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "person")
@Schema(name = "Пользователь")
public class Person {

  @Id
  @Schema(name = "Идентификатор")
  private Long id;

  @Column(name = "name")
  @Schema(name = "Имя")
  private String name;

  @Column(name = "age")
  @Schema(name = "Возраст", minimum = "0", maximum = "100")
  private Integer age;

}
