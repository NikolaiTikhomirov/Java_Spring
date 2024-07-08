package ru.gb.api;

import com.gb.aspect.TimeCounter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Book;
import ru.gb.repository.BookRepository;

import java.util.List;

@TimeCounter
@RestController
@RequestMapping("/book")
@Tag(name = "Library")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/{id}")
    @Operation(summary = "get book by ID", description = "Получить книгу по её ID")
    public Book getBookById (@PathVariable Long id) {
        return bookRepository.getBookById(id);
    }

    @GetMapping()
    @Operation(summary = "get all books", description = "Получить список всех книг")
    public List<Book> getAllBooks () {
        return bookRepository.findAll();
    }

    @PostMapping
    @Operation(summary = "add book", description = "Добавить книгу")
    public Book addBook (@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete book by ID", description = "Удалить книгу по её ID")
    public void deleteBookById (@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}
