package fpt.edu.vn.component;

import java.util.List;

import lombok.Data;

@Data
public class PostDTO {
    private long id;
    private String username;
    private String userImg;
    private String special;
    private String message;
    private String img;
    private long totalLike;
    private String time;
    private boolean isLiked;
    private List<CommentDTO> comments;
    private long userid;
    private long specialId;


}