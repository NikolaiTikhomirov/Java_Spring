package ru.gb;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RestCtrl {

  @GetMapping(value = "/api/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Person> persons(@PathVariable int id) {
    // ... ищем
    if (id < 10) {
      return ResponseEntity.ok(new Person("Igor", "Chestnov", 25, "github.com"));
    }

    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "не найден");

//    throw new NotFoundException("пользователь не найден");
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("не найден");
  }

}
