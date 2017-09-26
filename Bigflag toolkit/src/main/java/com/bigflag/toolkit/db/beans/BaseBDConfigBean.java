/**
 * 
 */
package com.bigflag.toolkit.db.beans;

import java.net.Inet4Address;

/**
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
 *         Create at:下午11:42:04 
 */
public class BaseBDConfigBean {
	private final String driver;
	private final String userName;
	private final String pwd;
	private final int port;
	private final String jdbcURL;
	private final Inet4Address ipAddress;
	private final int initConnectionCount;
	private final int maxConnectionCount;
	private final int connectionTimeout;
	private final int connectionIdelTime;
	/**
	 * @param driver
	 * @param userName
	 * @param pwd
	 * @param port
	 * @param ipAddress
	 * @param initConnectionCount
	 * @param maxConnectionCount
	 * @param connectionTimeout
	 * @param connectionIdelTime
	 */
	private BaseBDConfigBean(Builder builder) {
		super();
		this.driver = builder.driver;
		this.userName = builder.userName;
		this.pwd = builder.pwd;
		this.port = builder.port;
		this.ipAddress = builder.ipAddress;
		this.initConnectionCount = builder.initConnectionCount;
		this.maxConnectionCount = builder.maxConnectionCount;
		this.connectionTimeout = builder.connectionTimeout;
		this.connectionIdelTime = builder.connectionIdelTime;
		this.jdbcURL=builder.jdbcURL;
	}
	
	public static class Builder{
		private String driver;
		private String userName;
		private String pwd;
		private int port;
		private Inet4Address ipAddress;
		private int initConnectionCount;
		private int maxConnectionCount;
		private int connectionTimeout;
		private int connectionIdelTime;
		private String jdbcURL;
		
		public BaseBDConfigBean build()
		{
			return new BaseBDConfigBean(this);
		}
		
		public Builder setJdbcURL(String jdbcURL) {
			this.jdbcURL = jdbcURL;
			return this;
		}

		public Builder setDriver(String driver) {
			this.driver = driver;
			return this;
		}
		public Builder setUserName(String userName) {
			this.userName = userName;
			return this;
		}
		public Builder setPwd(String pwd) {
			this.pwd = pwd;
			return this;
		}
		public Builder setPort(int port) {
			this.port = port;
			return this;
		}
		public Builder setIpAddress(Inet4Address ipAddress) {
			this.ipAddress = ipAddress;
			return this;
		}
		public Builder setInitConnectionCount(int initConnectionCount) {
			this.initConnectionCount = initConnectionCount;
			return this;
		}
		public Builder setMaxConnectionCount(int maxConnectionCount) {
			this.maxConnectionCount = maxConnectionCount;
			return this;
		}
		public Builder setConnectionTimeout(int connectionTimeout) {
			this.connectionTimeout = connectionTimeout;
			return this;
		}
		public Builder setConnectionIdelTime(int connectionIdelTime) {
			this.connectionIdelTime = connectionIdelTime;
			return this;
		}
	}

	public String getDriver() {
		return driver;
	}

	public String getUserName() {
		return userName;
	}

	public String getPwd() {
		return pwd;
	}

	public String getJdbcURL() {
		return jdbcURL;
	}

	public int getPort() {
		return port;
	}

	public Inet4Address getIpAddress() {
		return ipAddress;
	}

	public int getInitConnectionCount() {
		return initConnectionCount;
	}

	public int getMaxConnectionCount() {
		return maxConnectionCount;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public int getConnectionIdelTime() {
		return connectionIdelTime;
	}
	
}
