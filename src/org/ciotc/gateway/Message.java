package org.ciotc.gateway;

import java.text.SimpleDateFormat;

public class Message {
	private byte[] data;
	SimpleDateFormat sdf = 
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
	public Message(){ 
		
	}
	public Message(byte[] data){
		this.data = data;
	}
	public byte[] getBytes(){
		byte[] ret = null;
		
		return ret;
	}
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < data.length; i ++){
			sb.append(Byte.toString(data[i]));
		}
		return sb.toString();
	}
	public Message heartBeat(){
		byte[] hb = new byte[2];
		hb[0] = (byte)2;
		hb[1] = (byte)3;
		return new Message(hb);
	}
	public static void main(String[] args) {
		

	}

}
