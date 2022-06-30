package fpt.edu.vn.component;

public class CommonMsg {
	private String msgCode;
	private String msgBody;
	private boolean isSuccess;
	
	public CommonMsg() {
	}
	
	public CommonMsg(String msgCode, String msgBody, boolean isSuccess) {
		super();
		this.msgCode = msgCode;
		this.msgBody = msgBody;
		this.isSuccess = isSuccess;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	@Override
	public String toString() {
		return "CommonMsg [msgCode=" + msgCode + ", msgBody=" + msgBody + ", isSuccess=" + isSuccess + "]";
	}
	
}
