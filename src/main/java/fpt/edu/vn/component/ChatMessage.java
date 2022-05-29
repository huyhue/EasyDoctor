package fpt.edu.vn.component;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private int sender_id;
    private String role;
    private String createdAt;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    
    public ChatMessage() {
	}

	public ChatMessage(String content, String sender, String role,String createdAt) {
		super();
		this.content = content;
		this.sender = sender;
		this.role = role;
		this.createdAt = createdAt;
	}

	public ChatMessage(String content, int sender_id) {
		super();
		this.content = content;
		this.sender_id = sender_id;
	}

	public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "ChatMessage [type=" + type + ", content=" + content + ", sender=" + sender + ", sender_id=" + sender_id
				+ "]";
	}
    
}
