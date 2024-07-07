package ru.gb.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import api.src.main.java.ru.gb.api.DetailedIssue;
import ru.gb.model.Issue;
import ru.gb.repository.IssueRepository;
import ru.gb.service.IssueServ;
import ru.gb.service.ReaderAlreadyHasBookException;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
@Tag(name = "Library")
public class IssueController {

  @Autowired
  private IssueServ service;
  @Autowired
  private IssueRepository issueRepository;

  @PostMapping
  @Operation(summary = "issue book", description = "выдать книгу")
  public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());

    final Issue issue;
    try {
      issue = service.issue(request);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    } catch (ReaderAlreadyHasBookException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(issue);
  }

  @GetMapping("{id}")
  @Operation(summary = "get issue by id", description = "Получить запись о выдаче книги по ID")
  public Issue getIssueById (@PathVariable Long id) {
    return service.getIssueById(id).getBody();
  }

  @GetMapping("reader/{id}")
  @Operation(summary = "get all issues by reader id", description = "Получить список всех записей о выдаче книг по ID читателя")
  public List<Issue> getAllIssuesByReaderId (@PathVariable Long id) {
    return issueRepository.getAllIssuesByReaderId(id);
  }

  @GetMapping
  @Operation(summary = "get all issues", description = "Получить список всех записей о выдаче книг")
  public List<DetailedIssue> getAllIssues () {
    return service.getAllIssues();
  }

  @PutMapping("{issueId}")
  @Operation(summary = "return book", description = "Сделать запись о возврате книги")
  public Issue returnBook (@PathVariable Long issueId) {
    return service.returnBook(issueId);
  }

}
