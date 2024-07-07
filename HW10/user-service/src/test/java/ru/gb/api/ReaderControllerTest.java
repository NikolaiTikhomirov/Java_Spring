package ru.gb.api;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.model.Reader;
import ru.gb.repository.ReaderRepository;

import java.util.List;
import java.util.Objects;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ReaderControllerTest {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ReaderRepository readerRepository;

    @Data
    static class JUnitReaderRequest {
        private String name;
        private String login;
        private String password;
        private String role;

        public JUnitReaderRequest(String name) {
            this.name = name;
            this.login = name;
            this.password = name;
            this.role = name;
        }
    }

    @Test
    void getReaderByIdSuccess() {
        Reader expected = readerRepository.save(new Reader("just reader"));

        Reader responseBody = webTestClient.get()
                .uri("/reader/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Reader.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
        Assertions.assertEquals(expected.getLogin(), responseBody.getLogin());
        Assertions.assertEquals(expected.getPassword(), responseBody.getPassword());
        Assertions.assertEquals(expected.getRole(), responseBody.getRole());
    }

    @Test
    void testFindByIdNotFound() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from readers", Long.class);

        webTestClient.get()
                .uri("/readers/" + maxId + 1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getReaderByName() {
        Reader expected = readerRepository.save(new Reader("superReader"));

        Reader responseBody = webTestClient.get()
                .uri("/reader/name/" + expected.getName())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Reader.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
        Assertions.assertEquals(expected.getLogin(), responseBody.getLogin());
        Assertions.assertEquals(expected.getPassword(), responseBody.getPassword());
        Assertions.assertEquals(expected.getRole(), responseBody.getRole());
    }

    @Test
    void getAllReaders() {
        readerRepository.saveAll(List.of(
                new Reader("first"),
                new Reader("second")
        ));

        List<Reader> expected = readerRepository.findAll();

        List<Reader> responseBody = webTestClient.get()
                .uri("/reader")
                // .retrieve
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Reader>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expected.size(), responseBody.size());
        for (Reader customerResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), customerResponse.getId()))
                    .filter(it -> Objects.equals(it.getName(), customerResponse.getName()))
                    .filter(it -> Objects.equals(it.getLogin(), customerResponse.getLogin()))
                    .filter(it -> Objects.equals(it.getPassword(), customerResponse.getPassword()))
                    .anyMatch(it -> Objects.equals(it.getRole(), customerResponse.getRole()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void addReader() {
        JUnitReaderRequest request = new JUnitReaderRequest("anyReader");

        Reader responseBody = webTestClient.post()
                .uri("/reader")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Reader.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(request.getName(), responseBody.getName());
        Assertions.assertEquals(request.getLogin(), responseBody.getLogin());
        Assertions.assertEquals(request.getPassword(), responseBody.getPassword());
        Assertions.assertEquals(request.getRole(), responseBody.getRole());

        Assertions.assertTrue(readerRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void deleteReaderById() {
        Reader request = readerRepository.save(new Reader("reader to delete"));

        webTestClient.delete()
                .uri("/reader/" + request.getId())
                .exchange()
                .expectStatus().isOk();

        Assertions.assertTrue(readerRepository.findById(request.getId()).isEmpty());
    }
}