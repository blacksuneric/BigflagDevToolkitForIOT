package com.bigflag.toolkit.ioc;

import com.bigflag.toolkit.db.interfaces.IDBService;
import com.bigflag.toolkit.iot.impl.DefaultIOTHandlerCenter;
import com.bigflag.toolkit.iot.interfaces.IIOTHandlerCenter;
import com.bigflag.toolkit.tool.cache.impl.RedisCacheImpl;
import com.bigflag.toolkit.tool.cache.interfaces.ICacheToolService;
import com.bigflag.toolkit.tool.http.impl.DefaultHttpPostToolImpl;
import com.bigflag.toolkit.tool.http.interfaces.IHttpToolService;

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
 *         Create at:下午10:51:57 
 */
public class ServiceFactory {
	private static Object locker = new Object();
	private static ServiceFactory instance;
	
	private final static DefaultIOTHandlerCenter defaultIOTHandlerCenter=new DefaultIOTHandlerCenter();
	private final static DefaultHttpPostToolImpl defaultHttpPostToolImpl=new DefaultHttpPostToolImpl();
	private final static RedisCacheImpl defaultCacheImpl=new RedisCacheImpl();
	
	private ServiceFactory() {}
	public static ServiceFactory getInstance() {
		if (instance == null) {
			synchronized (locker) {
				if (instance == null) {
					instance = new ServiceFactory();
				}
			}
		}
		return instance;
	}
	
	public IDBService getDBService(String serviceKey)
	{
		return null;
	}
	
	public IHttpToolService getHttpToolService(String serviceKey)
	{
		return null;
	}
	
	public IIOTHandlerCenter getDefaultIOTHandlerCenter()
	{
		return defaultIOTHandlerCenter;
	}
	
	public IHttpToolService getDefaultHttpPostToolImpl()
	{
		return defaultHttpPostToolImpl;
	}
	
	public ICacheToolService getDefaultCacheToolService()
	{
		return defaultCacheImpl;
	}

}