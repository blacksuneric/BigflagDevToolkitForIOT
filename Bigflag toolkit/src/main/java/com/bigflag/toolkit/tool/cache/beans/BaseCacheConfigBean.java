/**
 * 
 */
package com.bigflag.toolkit.tool.cache.beans;

import com.bigflag.toolkit.iot.hardware.beans.immutable.IOTEarthMagnetisumInfo.Builder;

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
 *         Create at:2017年9月20日 下午9:14:39
 */
public final class BaseCacheConfigBean {
	private final String serverHost;
	private final int port;
	public String getServerHost() {
		return serverHost;
	}
	public int getPort() {
		return port;
	}
	/**
	 * @param serverHost
	 * @param port
	 */
	private BaseCacheConfigBean(Builder builder) {
		super();
		this.serverHost = builder.serverHost;
		this.port = builder.port;
	}
	
	public static class Builder{
		private String serverHost;
		private int port;
		/**
		 * @param serverHost
		 * @param port
		 */
		public Builder(String serverHost, int port) {
			super();
			this.serverHost = serverHost;
			this.port = port;
		}
		public void setServerHost(String serverHost) {
			this.serverHost = serverHost;
		}
		public void setPort(int port) {
			this.port = port;
		}
		
		public BaseCacheConfigBean build()
		{
			return new BaseCacheConfigBean(this);
		}
		
	}

	@Override
	public String toString() {
		return "BaseCacheConfigBean [serverHost=" + serverHost + ", port=" + port + "]";
	}
	
	
}
