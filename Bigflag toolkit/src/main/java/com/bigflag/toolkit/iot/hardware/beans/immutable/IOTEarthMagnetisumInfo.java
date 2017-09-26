package com.bigflag.toolkit.iot.hardware.beans.immutable;
import java.util.Date;
/***
 * *
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
 *         Create at:2017年9月20日 下午1:11:12
 */
public final class IOTEarthMagnetisumInfo {
	private final String earthMagnetisumCode;
	private final int power;
	private final int carStatus;
	private final String hardwareServerMsgFrom;
	private final Date createTime;


	public String getEarthMagnetisumCode(){
		return this.earthMagnetisumCode;
	}

	public int getPower(){
		return this.power;
	}

	public int getCarStatus(){
		return this.carStatus;
	}

	public String getHardwareServerMsgFrom(){
		return this.hardwareServerMsgFrom;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	private IOTEarthMagnetisumInfo(Builder builder) {
		super();
		this.earthMagnetisumCode = builder.earthMagnetisumCode;
		this.power = builder.power;
		this.carStatus = builder.carStatus;
		this.hardwareServerMsgFrom = builder.hardwareServerMsgFrom;
		this.createTime = builder.createTime;
	}

	public static class Builder{
		private String earthMagnetisumCode;
		private int power;
		private int carStatus;
		private String hardwareServerMsgFrom;
		private Date createTime;
	
		public Builder(String earthMagnetisumCode)
		{
			this.earthMagnetisumCode=earthMagnetisumCode;
		}

		public Builder earthMagnetisumCode(String earthMagnetisumCode){
			this.earthMagnetisumCode=earthMagnetisumCode;
			return this;
		}	

		public Builder power(int power){
			this.power=power;
			return this;
		}	

		public Builder carStatus(int carStatus){
			this.carStatus=carStatus;
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

		public IOTEarthMagnetisumInfo build(){
			return new IOTEarthMagnetisumInfo(this);
		}
	}

}