import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Teachers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int salary;
    private int age;

    public Teachers() {
    }

    public Teachers(String name, int salary, int age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
    }
}
