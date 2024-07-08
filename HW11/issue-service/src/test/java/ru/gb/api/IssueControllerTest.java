package ru.gb.api;

import lombok.AllArgsConstructor;
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
import ru.gb.model.Issue;
import ru.gb.repository.IssueRepository;

import java.util.List;
import java.util.Objects;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class IssueControllerTest {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    IssueRepository issueRepository;

    @Data
    @AllArgsConstructor
    static class JUnitIssueRequest {
        private long bookId;
        private long readerId;
    }

    @Test
    @Disabled
    void issueBook() {
        JUnitIssueRequest request = (new JUnitIssueRequest(1, 1));

        Issue responseBody = webTestClient.post()
                .uri("/issue")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Issue.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(request.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(request.getReaderId(), responseBody.getReaderId());

        Assertions.assertTrue(issueRepository.findById(request.getBookId()).isPresent());
    }

    @Test
    void getIssueById() {
        Issue expected = issueRepository.save(new Issue());

        Issue responseBody = webTestClient.get()
                .uri("/issue/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Issue.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expected.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(expected.getIssuedAt(), responseBody.getIssuedAt());
        Assertions.assertEquals(expected.getReturnedAt(), responseBody.getReturnedAt());
    }

    @Test
    void testFindByIdNotFound() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from issues", Long.class);

        webTestClient.get()
                .uri("/issues/" + maxId + 1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getAllIssuesByReaderId() {
    }

    @Test
    @Disabled
    void getAllIssues() {
        issueRepository.saveAll(List.of(
                new Issue(),
                new Issue()
        ));

        List<Issue> expected = issueRepository.findAll();

        List<Issue> responseBody = webTestClient.get()
                .uri("/issue")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Issue>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expected.size(), responseBody.size());
        for (Issue customerResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), customerResponse.getId()))
                    .filter(it -> Objects.equals(it.getBookId(), customerResponse.getBookId()))
                    .filter(it -> Objects.equals(it.getReaderId(), customerResponse.getReaderId()))
                    .filter(it -> Objects.equals(it.getIssuedAt(), customerResponse.getIssuedAt()))
                    .anyMatch(it -> Objects.equals(it.getReturnedAt(), customerResponse.getReturnedAt()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void returnBook() {
        Issue expected = issueRepository.save(new Issue());

        Issue responseBody = webTestClient.put()
                .uri("/issue/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Issue.class)
                .returnResult().getResponseBody();

        Assertions.assertTrue(issueRepository.findById(expected.getId()).isPresent());
        Assertions.assertNotNull(responseBody.getReturnedAt());
    }
}