package com.rfidcn.gateway;

import java.io.Serializable;

public class StaffMessageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cardID; // 定位卡编号 
	
	private String antennID; // 天线编号

	private String baseID; // 基站编号
	
	private String time; // 定位时间
	
	public StaffMessageDto() {
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getAntennID() {
		return antennID;
	}

	public void setAntennID(String antennID) {
		this.antennID = antennID;
	}

	public String getBaseID() {
		return baseID;
	}

	public void setBaseID(String baseID) {
		this.baseID = baseID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
