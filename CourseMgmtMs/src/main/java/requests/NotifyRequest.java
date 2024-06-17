package requests;

import lombok.Data;

@Data
public class NotifyRequest {
    private String message;
    private Long studentId;
    private Long instructorId;
}
