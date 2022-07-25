package fpt.edu.vn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;

//import lombok.Data;

@Entity
//@Data
@Table(name = "comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long postId;
	private String message;
	private String createAt;
	private String updateAt;
	private Long parentId;
	private Long userId;

	public Comment() {

	}

	public Comment(Long id, Long postId, String message, String createAt, String updateAt, Long parentId, Long userId) {
		super();
		this.id = id;
		this.postId = postId;
		this.message = message;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.parentId = parentId;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", postId=" + postId + ", message=" + message + ", createAt=" + createAt
				+ ", updateAt=" + updateAt + ", parentId=" + parentId + ", userId=" + userId + "]";
	}

}