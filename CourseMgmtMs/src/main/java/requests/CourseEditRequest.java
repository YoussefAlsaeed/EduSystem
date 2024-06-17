package requests;

import lombok.Data;

@Data
public class CourseEditRequest {
    private Long courseId;
    private String newName;
    private String newDuration;
    private String newCategory;
    private int newCapacity;
    private Long adminId;
}
