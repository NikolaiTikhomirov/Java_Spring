package ru.gb.api;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.aspect.TimeCounter;
import ru.gb.model.Book;
import ru.gb.model.Issue;
import ru.gb.model.Reader;
import api.src.main.java.ru.gb.api.DetailedIssue;
import java.util.List;
import java.util.Objects;

@TimeCounter
@Controller
@RequestMapping("/ui")
public class SimpleController {
    private final WebClient webClient;

    public SimpleController(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.filter(loadBalancerExchangeFilterFunction).build();
    }

    @GetMapping("/books")
    public String getBooks (Model model) {
        List<Book> books = Objects.requireNonNull(webClient.get()
                        .uri("http://book-service/book")
                        .retrieve()
                        .toEntityList(Book.class)
                        .block())
                .getBody();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/readers")
    public String getReaders (Model model) {
        List<Reader> readers = Objects.requireNonNull(webClient.get()
                        .uri("http://user-service/reader")
                        .retrieve()
                        .toEntityList(Reader.class)
                        .block())
                .getBody();
        model.addAttribute("readers", readers);
        return "readers";
    }

    @GetMapping("/issues")
    public String getIssues (Model model) {
        List<DetailedIssue> issues = Objects.requireNonNull(webClient.get()
                        .uri("http://issue-service/issue")
                        .retrieve()
                        .toEntityList(DetailedIssue.class)
                        .block())
                .getBody();
        model.addAttribute("issues", issues);
        return "issues";
    }

    @GetMapping("/reader/{id}")
    public String getIssuesByReaderId (@PathVariable Long id, Model model) {
        List<Issue> readerIssues = Objects.requireNonNull(webClient.get()
                        .uri("http://issue-service/issue/reader/{id}", id)
                        .retrieve()
                        .toEntityList(Issue.class)
                        .block())
                .getBody();

        model.addAttribute("readerIssues", readerIssues);
        model.addAttribute("reader", Objects.requireNonNull(webClient.get()
                                                        .uri("http://user-service/reader/{id}", id)
                                                        .retrieve()
                                                        .bodyToMono(Reader.class)
                                                        .block()));
        return "readerIssues";
    }

    @GetMapping("/home")
    public String getHomePage (Model model) {
        model.addAttribute("home");
        return "home";
    }
}
