package ru.gb.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.app.api.CustomerResponse;
import ru.gb.app.model.Customer;
import ru.gb.app.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  public List<CustomerResponse> getAll() {
    return customerRepository.findAll().stream()
      .map(this::map)
      .collect(Collectors.toList());
  }

  public Optional<CustomerResponse> findById(Long id) {
    return customerRepository.findById(id)
      .map(this::map);
  }

  private CustomerResponse map(Customer customer) {
    CustomerResponse response = new CustomerResponse();
    response.setId(customer.getId());
    response.setName(customer.getName());
    return response;
  }

}
