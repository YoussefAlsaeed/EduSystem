package models;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Enrollment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;
    private Long courseId;
    private Long studentId; // As a foreign key

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

}
