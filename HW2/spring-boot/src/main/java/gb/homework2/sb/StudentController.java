package gb.homework2.sb;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class StudentController {

    private StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/student")
    public List<Student> getStudents () {
        return studentRepository.getAll();
    }

    @GetMapping("/student/{id}")
    public Student getStudentById (@PathVariable Long id) {
        return studentRepository.getById(id);
    }

    @GetMapping()
    public List<Student> getStudentsWhereNameContains (@RequestParam(required = false) String name) {
        return studentRepository.getByName(name);
    }

    @GetMapping("/group/{group}/student")
    public List<Student> getStudentsByGroup (@PathVariable(required = false) String group) {
        return studentRepository.getByGroup(group);
    }

    @DeleteMapping("/student/{id}")
    public void deleteStudent (@PathVariable Long id) {
        studentRepository.deleteById(id);
    }

    @PostMapping("/student")
    public void addStudent (@RequestParam(required = false) String name, String group) {
        studentRepository.addStudent(name, group);
    }
}
