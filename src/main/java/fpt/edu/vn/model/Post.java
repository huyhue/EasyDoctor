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
    private Long user_id;
    private Long special_id;
    private String message;
    private String img;
    private String likes;
    private Long total_like;
    private Long create_at;
    private Long update_at;

}