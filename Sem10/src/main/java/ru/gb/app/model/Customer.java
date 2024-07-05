package ru.gb.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class Customer {

  @Id // bigint
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  public static Customer ofName(String name) {
    Customer customer = new Customer();
    customer.setName(name);
    return customer;
  }

}
