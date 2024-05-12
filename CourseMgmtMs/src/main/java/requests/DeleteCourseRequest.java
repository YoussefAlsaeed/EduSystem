package requests;

import lombok.Data;

@Data
public class DeleteCourseRequest {
    private Long courseId;
    private Long adminId;
}
