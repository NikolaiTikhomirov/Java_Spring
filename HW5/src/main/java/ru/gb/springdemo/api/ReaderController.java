package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private IssueRepository issueRepository;

    @GetMapping("/{id}")
    public Reader getReaderById (@PathVariable Long id) {
        return readerRepository.getReaderById(id);
    }

    @GetMapping()
    public List<Reader> getAllReaders () {
        return readerRepository.findAll();
    }

    @GetMapping("{id}/issue")
    public List<Issue> getAllIssuesByReaderId (@PathVariable Long id) {
        return issueRepository.getAllIssuesByReaderId(id);
    }

    @PostMapping
    public void addReader (@RequestBody Reader reader) {
        readerRepository.save(reader);
    }

    @DeleteMapping("{id}")
    public void deleteReaderById (@PathVariable Long id) {
        readerRepository.deleteById(id);
    }
}
