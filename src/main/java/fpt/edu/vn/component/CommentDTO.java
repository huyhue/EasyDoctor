package fpt.edu.vn.component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String message;
    private String username;
    private String time;
    private String img;

}
