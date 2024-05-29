package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.api.IssueRequest;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
@Service
@RequiredArgsConstructor
public class IssueService {

  // спринг это все заинжектит
  private final BookRepository bookRepository;
  private final ReaderRepository readerRepository;
  private final IssueRepository issueRepository;

  public Issue issue(IssueRequest request) {
    if (bookRepository.getBookById(request.getBookId()) == null) {
      log.info("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerRepository.getReaderById(request.getReaderId()) == null) {
      log.info("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }
    // можно проверить, что у читателя нет книг на руках (или его лимит не превышает в Х книг)
    if (issueRepository.containsUserById(request.getReaderId())) {
      log.info("У читателя уже есть книга на руках");
//      return issueRepository.getIssueByReaderId(request.getReaderId());
      throw new ReaderAlreadyHasBookException("У читателя уже есть книга на руках");
    }

    Issue issue = new Issue(request.getBookId(), request.getReaderId());
    issueRepository.save(issue);
    return issue;
  }

  public Issue getIssueById (Long id) {
    return issueRepository.getIssueById(id);
  }

  public List<Issue> getAllIssues () {
    return issueRepository.getAllIssues();
  }

  public void returnBook (Long id) {
    getIssueById(id).setReturned_at(LocalDateTime.now());
  }

}
