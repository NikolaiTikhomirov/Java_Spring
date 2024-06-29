package ru.gb.model;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.gb.repository.BookRepository;

@Component
@AllArgsConstructor
public class RunAfterStartup {
    private BookRepository bookRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        bookRepository.save(new Book("война и мир"));
        bookRepository.save(new Book("метрвые души"));
        bookRepository.save(new Book("чистый код"));
    }
}
