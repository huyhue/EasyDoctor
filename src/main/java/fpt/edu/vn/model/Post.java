package fpt.edu.vn.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long specialId;
    private String message;
    private String img;
    private String likes;
    private Long totalLike;
    private String createAt;
    private String updateAt;

}