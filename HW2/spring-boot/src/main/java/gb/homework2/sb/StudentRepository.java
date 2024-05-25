package gb.homework2.sb;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class StudentRepository {
    private List<Student> students;

    public StudentRepository() {
        this.students = new ArrayList<>();
        students.add(new Student("Sergey", "dev"));
        students.add(new Student("Elisey", "manager"));
        students.add(new Student("Andrey", "dev"));
        students.add(new Student("Alexey", "manager"));
        students.add(new Student("Apolagey", "dev"));


    }

    public List<Student> getAll () {
        return List.copyOf(students);
    }

    public Student getById (Long id) {
        return students.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public List<Student> getByName (String name) {
        return students.stream()
                .filter(it -> it.getName().contains(name))
                .toList();
    }

    public List<Student> getByGroup (String group) {
        return students.stream()
                .filter(it -> Objects.equals(it.getGroupName(), group))
                .toList();
    }

    public void deleteById (Long id) {
        students.remove(getById(id));
    }

    public void addStudent (String name, String group) {
        students.add(new Student(name, group));
    }
}
