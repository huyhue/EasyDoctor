package fpt.edu.vn.component;

import java.util.List;

import lombok.Data;

//@Data
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
    
    public PostDTO() {
	}
    
	public PostDTO(long id, String username, String userImg, String special, String message, String img, long totalLike,
			String time, boolean isLiked, List<CommentDTO> comments) {
		super();
		this.id = id;
		this.username = username;
		this.userImg = userImg;
		this.special = special;
		this.message = message;
		this.img = img;
		this.totalLike = totalLike;
		this.time = time;
		this.isLiked = isLiked;
		this.comments = comments;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
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
	public long getTotalLike() {
		return totalLike;
	}
	public void setTotalLike(long totalLike) {
		this.totalLike = totalLike;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public boolean isLiked() {
		return isLiked;
	}
	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
	public List<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

    
}
