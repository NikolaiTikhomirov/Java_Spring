package gb.homework2.sb;

import org.springframework.aop.scope.ScopedObject;
import org.springframework.aop.scope.ScopedProxyFactoryBean;
import org.springframework.context.annotation.*;

@Configuration
public class ApplicationConfiguration {
    @Bean
    @Scope("Singleton")
    StudentRepository StudentRepository () {
        return new StudentRepository();
    }
}