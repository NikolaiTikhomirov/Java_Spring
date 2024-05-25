package gb.homework2.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

/*
1. Создать Spring-boot приложение с помощью http://start.spring.io/
2. Создать класс Student с полями: id, name, groupName
3. Создать контроллер, обрабатывающий входящие запросы:
3.1 GET /student/{id} - получить студеннта по id
3.2 GET /student - Получить всех студентов
3.3 GET /student/search?name='studentName' - получить список студентов, чье имя содержит подстроку studentName
3.4 GET /group/{groupName}/student - получить всех студентов группы
3.5 POST /student - создать студеента (принимает JSON) (отладиться можно с помощью Postman)
3.6 DELETE /student/{id} - удалить студента
4. При старте приложения, в программе должно быть создано 5-10 студентов.
 */
