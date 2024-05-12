package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;
    private Long courseId;
    private Long studentId; // As a foreign key

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

}
