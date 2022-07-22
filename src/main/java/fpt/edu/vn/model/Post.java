package fpt.edu.vn.model;

import javax.persistence.*;

import lombok.Data;


//@Data
@Entity
@Table(name = "posts")
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
    
    public Post() {
	}
    
	public Post(Long id, Long user_id, Long special_id, String message, String img, String likes, Long total_like,
			Long create_at, Long update_at) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.special_id = special_id;
		this.message = message;
		this.img = img;
		this.likes = likes;
		this.total_like = total_like;
		this.create_at = create_at;
		this.update_at = update_at;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getSpecial_id() {
		return special_id;
	}
	public void setSpecial_id(Long special_id) {
		this.special_id = special_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getLikes() {
		return likes;
	}
	public void setLikes(String likes) {
		this.likes = likes;
	}
	public Long getTotal_like() {
		return total_like;
	}
	public void setTotal_like(Long total_like) {
		this.total_like = total_like;
	}
	public Long getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Long create_at) {
		this.create_at = create_at;
	}
	public Long getUpdate_at() {
		return update_at;
	}
	public void setUpdate_at(Long update_at) {
		this.update_at = update_at;
	}

    
}