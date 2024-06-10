package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
@Entity
@Table(name = "issues")
public class Issue {

  public static long sequence = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final long id;
  @Column(name = "book_id")
  private final long bookId;
  @Column(name = "reader_id")
  private final long readerId;

  /**
   * Дата выдачи
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "issued_at")
  private final LocalDateTime issuedAt;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "returned_at")
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
