package ru.gb.springdemo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;

@RestController
@RequestMapping("/reader")
@Tag(name = "Library")
public class ReaderController {
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private IssueRepository issueRepository;

    @GetMapping("/{id}")
    @Operation(summary = "get reader by id", description = "Получить пользователя по его ID")
    public Reader getReaderById (@PathVariable Long id) {
        return readerRepository.getReaderById(id);
    }

    @GetMapping()
    @Operation(summary = "get all readers", description = "Получить список всех пользователей")
    public List<Reader> getAllReaders () {
        return readerRepository.findAll();
    }

    @GetMapping("{id}/issue")
    @Operation(summary = "get all issues by reader id", description = "Получить список выданных пользователю книг по его ID")
    public List<Issue> getAllIssuesByReaderId (@PathVariable Long id) {
        return issueRepository.getAllIssuesByReaderId(id);
    }

    @PostMapping
    @Operation(summary = "add reader", description = "Добавить пользователя")
    public void addReader (@RequestBody Reader reader) {
        readerRepository.save(reader);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete reader by id", description = "Удалить пользователя по его ID")
    public void deleteReaderById (@PathVariable Long id) {
        readerRepository.deleteById(id);
    }
}
