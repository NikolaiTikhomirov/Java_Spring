package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.springdemo.aspect.TimeCounter;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;

@TimeCounter
@Controller
@RequestMapping("/ui")
public class SimpleController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private IssueRepository issueRepository;

    @GetMapping("/books")
    public String getBooks (Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/readers")
    public String getReaders (Model model) {
        List<Reader> readers = readerRepository.findAll();
        model.addAttribute("readers", readers);
        return "readers";
    }

    @GetMapping("/issues")
    public String getIssues (Model model) {
        List<Issue> issues = issueRepository.findAll();
        model.addAttribute("issues", issues);
        return "issues";
    }

    @GetMapping("/reader/{id}")
    public String getIssuesByReaderId (@PathVariable Long id, Model model) {
        List<Issue> readerIssues = issueRepository.getAllIssuesByReaderId(id);
        model.addAttribute("readerIssues", readerIssues);
        model.addAttribute("reader", readerRepository.getReaderById(id));
        return "readerIssues";
    }

    @GetMapping("/home")
    public String getHomePage (Model model) {
        model.addAttribute("home");
        return "home";
    }
}
