package com.bigflag.toolkit.tool.socket.beans;
import java.util.Arrays;
import com.alibaba.fastjson.JSON;

public class SocketSessionMsgBean {
	private long sessionID;
	private String senderID;
	private byte[] data;

	public static SocketSessionMsgBean newInstance(){
		return new SocketSessionMsgBean();
	}

	public void setSessionID(long sessionID){
		this.sessionID=sessionID;
	}

	public long getSessionID(){
		return this.sessionID;
	}

	public SocketSessionMsgBean sessionID(long sessionID){
		this.sessionID=sessionID;
		return this;
	}

	public void setSenderID(String senderID){
		this.senderID=senderID;
	}

	public String getSenderID(){
		return this.senderID;
	}

	public SocketSessionMsgBean senderID(String senderID){
		this.senderID=senderID;
		return this;
	}

	public void setData(byte [] data){
		this.data=data;
	}

	public byte[] getData(){
		return this.data;
	}

	public SocketSessionMsgBean data(byte [] data){
		this.data=data;
		return this;
	}
	
	public String toJson()
	{
		return JSON.toJSONString(this);
	}

	@Override
	public String toString() {
		return "SocketSessionMsgBean [sessionID=" + sessionID + ", senderID=" + senderID + ", data=" + Arrays.toString(data) + "]";
	}
	
	
}