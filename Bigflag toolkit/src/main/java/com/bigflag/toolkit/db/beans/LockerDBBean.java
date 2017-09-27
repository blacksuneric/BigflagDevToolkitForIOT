package com.bigflag.toolkit.db.beans;

public class LockerDBBean extends BaseDBBean {
	private String lockerCode;
	private String lockerAddress;

	public static LockerDBBean newInstance(){
		return new LockerDBBean();
	}

	public void setLockerCode(String lockerCode){
		this.lockerCode=lockerCode;
	}

	public String getLockerCode(){
		return this.lockerCode;
	}

	public LockerDBBean lockerCode(String lockerCode){
		this.lockerCode=lockerCode;
		return this;
	}

	public void setLockerAddress(String lockerAddress){
		this.lockerAddress=lockerAddress;
	}

	public String getLockerAddress(){
		return this.lockerAddress;
	}

	public LockerDBBean lockerAddress(String lockerAddress){
		this.lockerAddress=lockerAddress;
		return this;
	}

	@Override
	public String toString() {
		return "LockerDBBean [lockerCode=" + lockerCode + ", lockerAddress=" + lockerAddress + "]";
	}
}