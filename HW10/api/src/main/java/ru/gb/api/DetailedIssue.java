package api.src.main.java.ru.gb.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import api.src.main.java.ru.gb.api.Book;
import api.src.main.java.ru.gb.api.Reader;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
@Entity
@Table(name = "issues")
@Schema(name = "Запись о выдаче")
public class DetailedIssue {

  @Schema(name = "Идентификатор")
  private Long id;
  @Column(name = "book")
  @Schema(name = "Книга")
  private Book book;
  @Column(name = "reader")
  @Schema(name = "Читатель")
  private Reader reader;

  /**
   * Дата выдачи
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "issued_at")
  @Schema(name = "Дата выдачи")
  private LocalDateTime issuedAt;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "returned_at")
  @Schema(name = "Дата возврата")
  private LocalDateTime returnedAt;

  public DetailedIssue() {
  }

  public DetailedIssue(long id, Book book, Reader reader, LocalDateTime issuedAt, LocalDateTime returnedAt) {
    this.id = id;
    this.book = book;
    this.reader = reader;
    this.issuedAt = issuedAt;
    this.returnedAt = returnedAt;
  }

}
