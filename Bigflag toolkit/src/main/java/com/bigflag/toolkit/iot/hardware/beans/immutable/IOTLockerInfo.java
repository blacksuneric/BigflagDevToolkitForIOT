package com.bigflag.toolkit.iot.hardware.beans.immutable;
import java.util.Date;
/***
 * 
 * Copyright 2017-2027 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Eric,Liu<br> 
 *		   mail:     34223022@qq.com<br>
 *         Create at:下午9:25:50
 */
public final class IOTLockerInfo {
	private final String lockerCode;
	private final int lockerPower;
	private final int lockerStatus;
	private final String actionCode;
	private final String exceptionCode;
	private final String hardwareServerMsgFrom;
	private final Date createTime;


	public String getLockerCode(){
		return this.lockerCode;
	}

	public int getLockerPower(){
		return this.lockerPower;
	}

	public int getLockerStatus(){
		return this.lockerStatus;
	}

	public String getActionCode(){
		return this.actionCode;
	}

	public String getExceptionCode(){
		return this.exceptionCode;
	}

	public String getHardwareServerMsgFrom(){
		return this.hardwareServerMsgFrom;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	private IOTLockerInfo(Builder builder) {
		super();
		this.lockerCode = builder.lockerCode;
		this.lockerPower = builder.lockerPower;
		this.lockerStatus = builder.lockerStatus;
		this.actionCode = builder.actionCode;
		this.exceptionCode = builder.exceptionCode;
		this.hardwareServerMsgFrom = builder.hardwareServerMsgFrom;
		this.createTime = builder.createTime;
	}

	public static class Builder{
		private String lockerCode;
		private int lockerPower;
		private int lockerStatus;
		private String actionCode;
		private String exceptionCode;
		private String hardwareServerMsgFrom;
		private Date createTime;
	

		/**
		 * @param string
		 */
		public Builder(String lockerCode) {
			this.lockerCode=lockerCode;
		}

		public Builder lockerCode(String lockerCode){
			this.lockerCode=lockerCode;
			return this;
		}	

		public Builder lockerPower(int lockerPower){
			this.lockerPower=lockerPower;
			return this;
		}	

		public Builder lockerStatus(int lockerStatus){
			this.lockerStatus=lockerStatus;
			return this;
		}	

		public Builder actionCode(String actionCode){
			this.actionCode=actionCode;
			return this;
		}	

		public Builder exceptionCode(String exceptionCode){
			this.exceptionCode=exceptionCode;
			return this;
		}	

		public Builder hardwareServerMsgFrom(String hardwareServerMsgFrom){
			this.hardwareServerMsgFrom=hardwareServerMsgFrom;
			return this;
		}	

		public Builder createTime(Date createTime){
			this.createTime=createTime;
			return this;
		}

		public IOTLockerInfo build(){
			return new IOTLockerInfo(this);
		}
	}

}