package ru.gb.springdemo.model;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.ReaderRepository;

@Component
@AllArgsConstructor
public class RunAfterStartup {
    private BookRepository bookRepository;
    private ReaderRepository readerRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        bookRepository.save(new Book("война и мир"));
        bookRepository.save(new Book("метрвые души"));
        bookRepository.save(new Book("чистый код"));

        readerRepository.save(new Reader("admin"));
        readerRepository.save(new Reader("reader"));
        readerRepository.save(new Reader("simple"));
    }
}
