package ru.gb.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.model.Reader;
import ru.gb.repository.ReaderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RestController
@RequestMapping("/reader")
@Tag(name = "Library")
public class ReaderController {
    @Autowired
    private ReaderRepository readerRepository;

    public ReaderController(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction, ReaderRepository readerRepository, WebClient.Builder webClientBuilder) {
        this.readerRepository = readerRepository;
        WebClient webClient = webClientBuilder.filter(loadBalancerExchangeFilterFunction).build();
//        webClient = WebClient.builder()
//                .filter(loadBalancerExchangeFilterFunction)
//                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "get reader by id", description = "Получить пользователя по его ID")
    public Reader getReaderById (@PathVariable Long id) {
        return readerRepository.getReaderById(id);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "get reader by name", description = "Получить пользователя по его имени")
    public Optional<Reader> getReaderByName (@PathVariable String name) {
        return readerRepository.findByLogin(name);
    }

    @GetMapping()
    @Operation(summary = "get all readers", description = "Получить список всех пользователей")
    public List<Reader> getAllReaders () {
        return readerRepository.findAll();
    }

//    @GetMapping("{id}/issue")
//    @Operation(summary = "get all issues by reader id", description = "Получить список выданных пользователю книг по его ID")
//    public List<Issue> getAllIssuesByReaderId (@PathVariable Long id) {
//        ResponseEntity<List<ResponseEntity>> issue = webClient.get()
//                .uri("http://issue-service/reader/{}/issue", id)
//                .retrieve()
//                .toEntityList(ResponseEntity.class)
//                .block();
//        return (List<Issue>) issue;
//    }

    @PostMapping
    @Operation(summary = "add reader", description = "Добавить пользователя")
    public Reader addReader (@RequestBody Reader reader) {
        return readerRepository.save(reader);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete reader by id", description = "Удалить пользователя по его ID")
    public void deleteReaderById (@PathVariable Long id) {
        readerRepository.deleteById(id);
    }
}
