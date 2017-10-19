package com.bigflag.toolkit.ioc;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.NotImplementedException;

import com.bigflag.toolkit.db.impl.C3P0DBService;
import com.bigflag.toolkit.db.impl.MongoDBService;
import com.bigflag.toolkit.db.interfaces.IDBService;
import com.bigflag.toolkit.db.interfaces.IMongoDBService;
import com.bigflag.toolkit.iot.encryptedcodedevice.impl.DefaultEncryptedCodeDeviceService;
import com.bigflag.toolkit.iot.encryptedcodedevice.interfaces.IEncryptedCodeDeviceService;
import com.bigflag.toolkit.iot.impl.DefaultIOTHandlerCenter;
import com.bigflag.toolkit.iot.interfaces.IIOTHandlerCenter;
import com.bigflag.toolkit.iot.nbiot.impl.NBIOTService;
import com.bigflag.toolkit.iot.nbiot.interfaces.INBIOTService;
import com.bigflag.toolkit.rpc.impl.DefaultRemoteCallService;
import com.bigflag.toolkit.rpc.interfaces.IRemoteCallService;
import com.bigflag.toolkit.tool.cache.impl.RedisCacheImpl;
import com.bigflag.toolkit.tool.cache.interfaces.ICacheToolService;
import com.bigflag.toolkit.tool.http.impl.DefaultHttpPostToolImpl;
import com.bigflag.toolkit.tool.http.interfaces.IHttpToolService;
import com.bigflag.toolkit.tool.mq.impl.RabbitMQService;
import com.bigflag.toolkit.tool.mq.interfaces.IMQToolService;
import com.bigflag.toolkit.tool.socket.impl.mina.SocketTCPSerivceMinaImpl;
import com.bigflag.toolkit.tool.socket.impl.mina.SocketUDPSerivceMinaImpl;
import com.bigflag.toolkit.tool.socket.interfaces.ISocketTCPService;
import com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService;

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
	
	private final static IDBService defaultDBService=new C3P0DBService();
	private final static IIOTHandlerCenter defaultIOTHandlerCenter=new DefaultIOTHandlerCenter();
	private final static IHttpToolService defaultHttpPostToolImpl=new DefaultHttpPostToolImpl();
	private final static ICacheToolService defaultCacheImpl=new RedisCacheImpl();
	private final static ISocketTCPService defaultSocketTCPService=new SocketTCPSerivceMinaImpl();
	private final static ISocketUDPService defaultSocketUDPService=new SocketUDPSerivceMinaImpl();
	private final static IMQToolService defaultMQService=new RabbitMQService();
	private final static INBIOTService defaultNBIOTService=new NBIOTService();
	private final static IEncryptedCodeDeviceService defaultEncryptedCodeDeviceService=new DefaultEncryptedCodeDeviceService();
	private final static IMongoDBService defaultMongoDBService=new MongoDBService();
	private final static IRemoteCallService defaultRemoteCallService=new DefaultRemoteCallService();
	
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
	
	public IDBService getDBService(String serviceClassName)
	{
		try {
			return (IDBService) ClassUtils.getClass(serviceClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public IIOTHandlerCenter getIIOTHandlerCenter(String serviceClassName)
	{
		try {
			return (IIOTHandlerCenter) ClassUtils.getClass(serviceClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public IHttpToolService getHttpToolService(String serviceClassName)
	{
		try {
			return (IHttpToolService) ClassUtils.getClass(serviceClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public ICacheToolService getCacheToolService(String serviceClassName)
	{
		try {
			return (ICacheToolService) ClassUtils.getClass(serviceClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public ISocketTCPService getSocketTCPService(String serviceClassName)
	{
		try {
			return (ISocketTCPService) ClassUtils.getClass(serviceClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public ISocketUDPService getSocketUDPService(String serviceClassName)
	{
		try {
			return (ISocketUDPService) ClassUtils.getClass(serviceClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public IMQToolService getMQToolService(String serviceClassName)
	{
		try {
			return (IMQToolService) ClassUtils.getClass(serviceClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public INBIOTService getNBIOTService(String serviceClassName)
	{
		try {
			return (INBIOTService) ClassUtils.getClass(serviceClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public IEncryptedCodeDeviceService getEncryptedCodeDeviceService(String serviceClassName)
	{
		try {
			return (IEncryptedCodeDeviceService) ClassUtils.getClass(serviceClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public IMongoDBService getMongoDBService(String serviceClassName)
	{
		try {
			return (IMongoDBService) ClassUtils.getClass(serviceClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public IDBService getDefaultDBService()
	{
		return defaultDBService;
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
	
	public ISocketTCPService getDefaultSocketTCPService()
	{
		return defaultSocketTCPService;
	}
	
	public ISocketUDPService getDefaultSocketUDPService()
	{
		return defaultSocketUDPService;
	}
	
	public IMQToolService getDefaultMQService()
	{
		return defaultMQService;
	}

	public INBIOTService getDefaultNBIOTService()
	{
		return defaultNBIOTService;
	}
	
	public IEncryptedCodeDeviceService getDefaultEncryptedCodeDeviceService()
	{
		return defaultEncryptedCodeDeviceService;
	}
	
	public IMongoDBService getDefaultMongoDBService()
	{
		return defaultMongoDBService;
	}
	
	public IRemoteCallService getDefaultRemoteCallService()
	{
		return defaultRemoteCallService;
	}
}