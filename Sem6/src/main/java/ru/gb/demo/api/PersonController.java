package ru.gb.demo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.demo.model.Person;
import ru.gb.demo.repository.PersonRepository;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
@Tag(name = "Person")
public class PersonController {

  // OpenAPI3 -

  private final PersonRepository repository;

  @RequestMapping(method = RequestMethod.GET)
  @Operation(summary = "get all persons", description = "Загружает всех пользоватлеей, которые есть в системе")
  public ResponseEntity<List<Person>> getAll() {
    return ResponseEntity.ok(repository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Person> getAll(@PathVariable Long id) {
    return ResponseEntity.ok(repository.findById(id).orElseThrow());
  }

  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH})
  public ResponseEntity<Person> save(@RequestBody Person person) {
    return ResponseEntity.ok(repository.save(person));
  }

}
