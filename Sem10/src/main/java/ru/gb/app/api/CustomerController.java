package ru.gb.app.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.app.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService service;

  @GetMapping
  public List<CustomerResponse> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponse> getById(@PathVariable Long id) {
    return service.findById(id)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

}
