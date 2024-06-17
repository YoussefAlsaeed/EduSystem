package models;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonIgnore
    private List<Reviews> reviews;

    @OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Enrollment> enrollments;

    private String name;
    private String duration;
    private String category;
    private double rating;
    private int totalRates;
    private int capacity;
    private int enrolledStudents;

    public enum ContentReviewStatus {
        ACCEPTED,
        REJECTED,
        PENDING
    }

    @Enumerated(EnumType.STRING)
    private ContentReviewStatus contentReviewed;
    private Long instructorId;

    public void addToReviews(Reviews rev){
        this.reviews.add(rev);
    }

}