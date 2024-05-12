package models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class StudentCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Long courseId;
    private Long studentId;

}
