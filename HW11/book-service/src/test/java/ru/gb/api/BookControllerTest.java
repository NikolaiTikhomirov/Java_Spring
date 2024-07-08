package ru.gb.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.model.Book;
import ru.gb.repository.BookRepository;

import java.util.List;
import java.util.Objects;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BookControllerTest {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    BookRepository bookRepository;

    @Data
    @AllArgsConstructor
    static class JUnitBookRequest {
        private String name;
    }

    @Test
    void getBookByIdSuccess() {
        Book expected = bookRepository.save(new Book("just book"));

        Book responseBody = webTestClient.get()
                .uri("/book/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
    }

    @Test
    void testFindByIdNotFound() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from books", Long.class);

        webTestClient.get()
                .uri("/book/" + maxId + 1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getAllBooks() {
        bookRepository.saveAll(List.of(
                new Book("first"),
                new Book("second")
        ));

        List<Book> expected = bookRepository.findAll();

        List<Book> responseBody = webTestClient.get()
                .uri("/book")
                // .retrieve
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Book>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expected.size(), responseBody.size());
        for (Book customerResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), customerResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), customerResponse.getName()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void addBook() {
        JUnitBookRequest request = new JUnitBookRequest("some book");

        Book responseBody = webTestClient.post()
                .uri("/book")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(request.getName(), responseBody.getName());

        Assertions.assertTrue(bookRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void deleteBookById() {
        Book request = bookRepository.save(new Book("book to delete"));

        webTestClient.delete()
                .uri("/book/" + request.getId())
                .exchange()
                .expectStatus().isOk();

        Assertions.assertTrue(bookRepository.findById(request.getId()).isEmpty());
    }
}