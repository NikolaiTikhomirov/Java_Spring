package ru.gb.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.context.XmlServletWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import ru.gb.demo.model.Person;
import ru.gb.demo.repository.PersonRepository;
import ru.gb.demo.service.PersonService;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties(ReaderProperties.class)
public class DemoApplication {

  // /META-INF/spring.factories

  // org.springframework -> spring-web.jar
  // org.springframework.boot -> spring-boot-starter-web.jar [spring-web]

  // com.gb..... my-starter.jar [/META-INF/spring.factories]

  private final PersonRepository repository;
  private final PersonService service;

  public static void main(String[] args) {
//    new AnnotationConfigApplicationContext(DemoApplication.class);
    ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
    ReaderProperties readerProperties = context.getBean(ReaderProperties.class);

    log.info("tags: {}", readerProperties.getTags());
  }

//  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() {
    Person person = preparePerson();
    try {
      service.updatePerson(person.getId(), "Updated", 25);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

		repository.findById(person.getId())
			.ifPresent(System.out::println);
	}

	private Person preparePerson() {
    Person person = new Person();
    person.setId(1L);
    person.setName("Initial");
    person.setAge(50);
    return repository.save(person);
  }

}
