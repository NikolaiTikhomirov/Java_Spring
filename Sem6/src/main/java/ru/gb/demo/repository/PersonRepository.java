package ru.gb.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.demo.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
