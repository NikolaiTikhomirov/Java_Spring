package ru.gb.springdemo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.service.IssueService;
import ru.gb.springdemo.service.ReaderAlreadyHasBookException;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
@Tag(name = "Library")
public class IssueController {

  @Autowired
  private IssueService service;

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

  @GetMapping
  @Operation(summary = "get all issues", description = "Получить список всех записей о выдаче книг")
  public List<Issue> getAllIssues () {
    return service.getAllIssues();
  }

  @PutMapping("{issueId}")
  @Operation(summary = "return book", description = "Сделать запись о возврате книги")
  public void returnBook (@PathVariable Long issueId) {
    service.returnBook(issueId);
  }

}
