package requests;

import lombok.Data;

@Data
public class CourseEditRequest {
    private Long courseId;
    private String newName;
    private Long adminId;
}
