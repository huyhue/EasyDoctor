package fpt.edu.vn.model;

import javax.persistence.*;

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
	
	private String createAt;
	private String updateAt;

	public Post(Long id, Long userId, Long specialId, String message, String img, String likes, Long totalLike,
			String createAt, String updateAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.specialId = specialId;
		this.message = message;
		this.img = img;
		this.likes = likes;
		this.totalLike = totalLike;
		this.createAt = createAt;
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

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", userId=" + userId + ", specialId=" + specialId + ", message=" + message + ", img="
				+ img + ", likes=" + likes + ", totalLike=" + totalLike + ", createAt=" + createAt + ", updateAt="
				+ updateAt + "]";
	}

}