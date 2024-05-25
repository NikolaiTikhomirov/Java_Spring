package gb.homework2.sb;

import lombok.Data;

@Data
public class Student {
    private static Long idCounter = 1L;

    private final Long id;
    private String name;
    private String groupName;

    public Student (String name, String groupName) {
        this.id = idCounter++;
        this.name = name;
        this.groupName = groupName;
    }
}
