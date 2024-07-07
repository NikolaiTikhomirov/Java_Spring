package ru.gb.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.IssueRequest;
import api.src.main.java.ru.gb.api.Book;
import api.src.main.java.ru.gb.api.Reader;
import api.src.main.java.ru.gb.api.DetailedIssue;
import ru.gb.model.Issue;
import ru.gb.repository.IssueRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
public class IssueServ {

  // спринг это все заинжектит
  private final IssueRepository issueRepository;
  private final WebClient webClient;

  @Value("${application.max-allowed-books}")
  private int maxAllowedBooks;

  public IssueServ(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction, IssueRepository issueRepository, WebClient.Builder webClientBuilder) {
    this.issueRepository = issueRepository;
    this.webClient = webClientBuilder.filter(loadBalancerExchangeFilterFunction).build();
  }

  public Issue issue(IssueRequest request) {
    long bookId = request.getBookId();
    long readerId = request.getReaderId();
    Book book = webClient.get()
            .uri("http://book-service/book/{bookId}", bookId)
            .retrieve()
            .bodyToMono(Book.class)
            .block();

    Reader reader = webClient.get()
            .uri("http://user-service/reader/{readerId}", readerId)
            .retrieve()
            .bodyToMono(Reader.class)
            .block();

    if (book == null) {
      log.info("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (reader == null) {
      log.info("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }
    // можно проверить, что у читателя нет книг на руках (или его лимит не превышает в Х книг)
    if (issueRepository.getAllIssuesByReaderId(request.getReaderId()).size() >= maxAllowedBooks) {
      log.info("У читателя уже достаточно книг на руках");
//      return issueRepository.getIssueByReaderId(request.getReaderId());
      throw new ReaderAlreadyHasBookException("У читателя уже достаточно книг на руках");
    }

    Issue issue = new Issue(request.getBookId(), request.getReaderId());
    issueRepository.save(issue);
    return issue;
  }

  public ResponseEntity<Issue> getIssueById (Long id) {
    Optional<Issue> issue = issueRepository.findById(id);
    return issue.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    return issueRepository.getIssueById(id);
  }

  public List<DetailedIssue> getAllIssues () {
    List<Issue> allIssues = issueRepository.findAll();
    List<DetailedIssue> detailedListIssues = new ArrayList<>();
    for (Issue re : allIssues) {
      long bookId = re.getBookId();
      Book book = webClient.get()
              .uri("http://book-service/book/{bookId}", bookId)
              .retrieve()
              .bodyToMono(Book.class)
              .block();
      long readerId = re.getReaderId();
      Reader reader = webClient.get()
              .uri("http://user-service/reader/{readerId}", readerId)
              .retrieve()
              .bodyToMono(Reader.class)
              .block();
      DetailedIssue detailedIssue = new DetailedIssue(re.getId(), book, reader, re.getIssuedAt(), re.getReturnedAt());
      detailedListIssues.add(detailedIssue);
    }
    return detailedListIssues;
  }

  public Issue returnBook (Long id) {
    Issue issue = getIssueById(id).getBody();
    issue.setReturnedAt(LocalDateTime.now());
    return issueRepository.save(issue);
  }

}
