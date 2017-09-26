package com.bigflag.toolkit.tool.mq.beans;
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
 *         Create at:2017年9月21日 上午11:46:24
 */
public class BaseMQConfigBean {
	private final String serverAddress;
	private final int port;


	public String getServerAddress(){
		return this.serverAddress;
	}

	public int getPort(){
		return this.port;
	}

	private BaseMQConfigBean(Builder builder) {
		super();
		this.serverAddress = builder.serverAddress;
		this.port = builder.port;
	}

	public static class Builder{
		private String serverAddress;
		private int port;
	

		public Builder serverAddress(String serverAddress){
			this.serverAddress=serverAddress;
			return this;
		}	

		public Builder port(int port){
			this.port=port;
			return this;
		}

		public BaseMQConfigBean build(){
			return new BaseMQConfigBean(this);
		}
	} 

}