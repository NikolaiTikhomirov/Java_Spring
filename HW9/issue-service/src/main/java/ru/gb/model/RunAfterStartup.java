package ru.gb.model;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RunAfterStartup {

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {

    }
}
