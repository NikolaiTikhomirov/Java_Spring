package ru.gb.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

  Issue getIssueById (Long id);

  List<Issue> getAllIssuesByReaderId (Long id);

}
