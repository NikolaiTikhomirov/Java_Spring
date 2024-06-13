package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/{id}")
    public Book getBookById (@PathVariable Long id) {
        return bookRepository.getBookById(id);
    }

    @GetMapping()
    public List<Book> getAllBooks () {
        return bookRepository.findAll();
    }

    @PostMapping
    public void addBook (@RequestBody Book book) {
        bookRepository.save(book);
    }

    @DeleteMapping("{id}")
    public void deleteBookById (@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}
