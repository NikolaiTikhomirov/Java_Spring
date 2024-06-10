package ru.gb.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.ReaderRepository;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		BookRepository bookRepository = context.getBean(BookRepository.class);
		ReaderRepository readerRepository = context.getBean(ReaderRepository.class);

		bookRepository.save(new Book("война и мир"));
		bookRepository.save(new Book("метрвые души"));
		bookRepository.save(new Book("чистый код"));

		readerRepository.save(new Reader("Игорь"));

	}
}
