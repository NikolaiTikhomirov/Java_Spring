package api.src.main.java.ru.gb.api;

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

  @Column(name = "login")
  private final String login;

  @Column(name = "password")
  private final String password;

  @Column(name = "role")
  private final String role;

  public Reader() {
    this(sequence++, "unknown", "unknown", "unknown", "unknown");
  }

  public Reader(String name) {
    this(sequence++, name, name, name, name);
  }

  public Reader(String name, String role) {
    this(sequence++, name, name, name, role);
  }

  public Reader(String name, String login, String password, String role) {
    this(sequence++, name, login, password, role);
  }

}
