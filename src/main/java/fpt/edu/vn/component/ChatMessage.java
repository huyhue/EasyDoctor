package fpt.edu.vn.component;

public class ChatMessage {
    private MessageType type;
    private String content;
    
    private String sender;
    private int sender_id;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    
    public ChatMessage() {
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

	@Override
	public String toString() {
		return "ChatMessage [type=" + type + ", content=" + content + ", sender=" + sender + ", sender_id=" + sender_id
				+ "]";
	}
    
}
