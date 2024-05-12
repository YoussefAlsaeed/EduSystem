package models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private Long courseId;
    private Long studentId;
    private String comment;
}
