package ru.gb.springdemo.repository;

import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class IssueRepository {

  private final List<Issue> issues;

  public IssueRepository() {
    this.issues = new ArrayList<>();
  }

  public void save(Issue issue) {
    // insert into ....
    issues.add(issue);
  }

  public Issue getIssueById (Long id) {
    return issues.stream().filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElse(null);
  }

  public Issue getIssueByReaderId (Long id) {
    return issues.stream()
            .filter(it -> Objects.equals(it.getReaderId(), id))
            .findFirst()
            .orElse(null);
  }

  public List<Issue> getAllIssuesByReaderId (Long id) {
    return issues.stream()
            .filter(it -> Objects.equals(it.getReaderId(), id))
            .toList();
  }

  public List<Issue> getAllIssues () {
    return List.copyOf(issues);
  }

  public Boolean containsUserById(Long id) {
    return issues.stream()
            .anyMatch(it -> Objects.equals(it.getReaderId(), id) && it.getReturned_at() == null);
  }
}
