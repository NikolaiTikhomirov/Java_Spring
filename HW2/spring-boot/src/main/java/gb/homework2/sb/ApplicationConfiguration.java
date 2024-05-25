package gb.homework2.sb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    StudentRepository myStudentRepository () {
        return new StudentRepository();
    }


}
