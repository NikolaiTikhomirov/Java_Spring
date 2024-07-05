package ru.gb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
@Entity
@Table(name = "issues")
@Schema(name = "Запись о выдаче")
public class Issue {

  public static long sequence = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(name = "Идентификатор")
  private final long id;
  @Column(name = "book_id")
  @Schema(name = "ID книги")
  private final long bookId;
  @Column(name = "reader_id")
  @Schema(name = "ID читателя")
  private final long readerId;

  /**
   * Дата выдачи
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "issued_at")
  @Schema(name = "Дата выдачи")
  private final LocalDateTime issuedAt;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "returned_at")
  @Schema(name = "Дата возврата")
  private LocalDateTime returnedAt;

  public Issue() {
    this.id = sequence++;
    this.bookId = 0L;
    this.readerId = 0L;
    this.issuedAt = LocalDateTime.now();
  }

  public Issue(long bookId, long readerId) {
    this.id = sequence++;
    this.bookId = bookId;
    this.readerId = readerId;
    this.issuedAt = LocalDateTime.now();
  }

}
