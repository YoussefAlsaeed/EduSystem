package requests;

import lombok.Data;
import models.EnrollmentStatus;

import java.io.Serializable;

@Data
public class EnrollmentMessage implements Serializable {
    private Long courseId;
    private Long studentId; // As a foreign key
    private EnrollmentStatus status;
}
