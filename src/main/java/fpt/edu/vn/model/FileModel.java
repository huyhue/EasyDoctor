package fpt.edu.vn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "file_model")
public class FileModel extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "content_type")
    private String contentType;
    
    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "data")
    private byte[] data;
    
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public FileModel() {
    }

    public FileModel(String name, String contentType, String type, byte[] data, User user) {
		super();
		this.name = name;
		this.contentType = contentType;
		this.type = type;
		this.data = data;
		this.user = user;
	}

	public FileModel(String name, String contentType, byte[] data) {
        this.name = name;
        this.contentType = contentType;
        this.data = data;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
}