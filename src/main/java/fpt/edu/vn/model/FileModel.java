package fpt.edu.vn.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "file_model")
public class FileModel extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(name = "data")
    private byte[] data;
    
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "id_history")
    private History history;

    public FileModel() {
    }

	public FileModel(String name, String contentType, byte[] data, User user, History history) {
		super();
		this.name = name;
		this.contentType = contentType;
		this.data = data;
		this.user = user;
		this.history = history;
	}

	public FileModel(String name, String contentType, byte[] data, User user) {
		super();
		this.name = name;
		this.contentType = contentType;
		this.data = data;
		this.user = user;
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

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}
    
}