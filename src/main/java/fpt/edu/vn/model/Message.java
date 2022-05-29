package fpt.edu.vn.model;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity implements Comparable<Message>, Serializable {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "id_appointment")
    private Appointment appointment;

    public Message() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public int compareTo(Message o) {
        return this.createdAt.compareTo(o.getCreatedAt());
    }

	@Override
	public String toString() {
		return "Message [createdAt=" + createdAt + ", message=" + message + ", author=" + author + ", appointment="
				+ appointment + "]";
	}
    
}
