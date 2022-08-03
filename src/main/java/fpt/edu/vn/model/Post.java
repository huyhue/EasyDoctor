package fpt.edu.vn.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "posts")
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
	
	@Column(name = "updateAt")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updateAt;

	public Post(Long id, Long userId, Long specialId, String message, String img, String likes, Long totalLike,
			LocalDateTime updateAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.specialId = specialId;
		this.message = message;
		this.img = img;
		this.likes = likes;
		this.totalLike = totalLike;
		this.updateAt = updateAt;
	}

	public Post() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSpecialId() {
		return specialId;
	}

	public void setSpecialId(Long specialId) {
		this.specialId = specialId;
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

	public Long getTotalLike() {
		return totalLike;
	}

	public void setTotalLike(Long totalLike) {
		this.totalLike = totalLike;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", userId=" + userId + ", specialId=" + specialId + ", message=" + message + ", img="
				+ img + ", likes=" + likes + ", totalLike=" + totalLike + ", updateAt=" + updateAt + "]";
	}

}