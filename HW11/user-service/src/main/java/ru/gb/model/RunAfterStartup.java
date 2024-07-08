package ru.gb.model;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.gb.repository.ReaderRepository;

@Component
@AllArgsConstructor
public class RunAfterStartup {
    private ReaderRepository readerRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {

        readerRepository.save(new Reader("admin"));
        readerRepository.save(new Reader("reader"));
        readerRepository.save(new Reader("simple"));
    }
}
