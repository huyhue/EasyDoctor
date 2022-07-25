package fpt.edu.vn.component;

//import lombok.AllArgsConstructor;
//import lombok.Data;

//@Data
//@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String message;
    private String username;
    private String time;
    private String img;
    private Long userid;
    
    public CommentDTO() {
	}
	public CommentDTO(Long id, String message, String username, String time, String img, Long userid) {
		super();
		this.id = id;
		this.message = message;
		this.username = username;
		this.time = time;
		this.img = img;
		this.userid = userid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	@Override
	public String toString() {
		return "CommentDTO [id=" + id + ", message=" + message + ", username=" + username + ", time=" + time + ", img="
				+ img + ", userid=" + userid + "]";
	}

}