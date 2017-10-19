/**
 * 
 */
package com.bigflag.toolkit.rpc.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.bigflag.toolkit.exception.RPCServiceNotInitException;
import com.bigflag.toolkit.rpc.beans.BaseRPCConfig;
import com.bigflag.toolkit.rpc.beans.RPCMessageProtobuf;
import com.bigflag.toolkit.rpc.beans.RPCMessageProtobuf.Message.Builder;
import com.bigflag.toolkit.rpc.beans.RemoteInterfaceInfoProtobuf;
import com.bigflag.toolkit.rpc.interfaces.IRemoteCallService;
import com.bigflag.toolkit.tool.coordinator.beans.BaseCoordinatorConfigBean;
import com.bigflag.toolkit.tool.coordinator.impl.DefaultZooKeeperCoordinatorService;
import com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Copyright 2017-2027 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * @author Eric,Liu<br>
 *         mail: 34223022@qq.com<br>
 *         Create at:2017年10月14日 下午7:09:36
 */
public class DefaultRemoteCallService implements IRemoteCallService {
	private ICoordinatorToolService zookeeperService = new DefaultZooKeeperCoordinatorService();
	private boolean isInit = true;
	private Map<Object, byte[]> objectBytesCache = new WeakHashMap<Object, byte[]>();
	private String esbServiceRootPath = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.RPC.IRemoteCallService#buildStub(java.lang.Class)
	 */
	@Override
	public <T> T buildStub(Class<T> clazz) {
		this.checkInit();
		Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("invoke " + method.getName() + " hello");
				if (method.getReturnType().getTypeName().equals("int")) {
					return 1;
				} else if (method.getReturnType().getTypeName().equals(String.class.getTypeName())) {
					return "asdf";
				}
				Builder builder = RPCMessageProtobuf.Message.newBuilder().setInterfaceFullName(clazz.getName()).setVersion(1)
						.setMethodName(method.getName());
				for (Object arg : args) {
					builder.addMethodParameter(ByteString.copyFrom(objectToByteArray(arg)));
				}
				byte[] sentRemoteInterfaceData = builder.build().toByteArray();
				throw new Exception("sdf");
				// return "result returned";
			}
		});
		return (T) obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.RPC.IRemoteCallService#connectToESB(com.bigflag.toolkit
	 * .RPC.BaseRPCConfig)
	 */
	@Override
	public boolean connectToESB(BaseRPCConfig esbConfig) {
		if (StringUtils.isNotBlank(esbConfig.getEsbServiceRootPath())) {

			this.isInit = zookeeperService.connectServer(new BaseCoordinatorConfigBean.Builder().connectUrl(esbConfig.getEsbURL())
					.timeout(esbConfig.getTimeout()).build());
			this.esbServiceRootPath = esbConfig.getEsbServiceRootPath();
			return isInit;
		} else {
			throw new IllegalArgumentException("ESB service root cannot be empty");
		}
	}

	private void checkInit() {
		if (!this.isInit) {
			throw new RPCServiceNotInitException();
		}
	}

	public byte[] objectToByteArray(Object obj) {
		byte[] bytes = objectBytesCache.get(obj);
		if (bytes != null) {
			return bytes;
		}
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			bytes = byteArrayOutputStream.toByteArray();
			objectBytesCache.put(obj, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return bytes;
	}

	@Override
	public List<RemoteInterfaceInfoProtobuf.Message> queryRemoteInterfaceInfo(String fullInterfaceName, int version, List<String> tags) {
		String serviceNode=esbServiceRootPath+"/"+fullInterfaceName;
		List<RemoteInterfaceInfoProtobuf.Message> interfaceMsgs=new ArrayList<RemoteInterfaceInfoProtobuf.Message>();
		if(!zookeeperService.existNode(serviceNode, false, null))
		{
			return new ArrayList<RemoteInterfaceInfoProtobuf.Message>();
		}else
		{
			List<String> serverNodes=zookeeperService.getNodeChildren(serviceNode, false, null);
			for(String serverNode:serverNodes)
			{
				byte[] data=zookeeperService.getNodeData(serviceNode+"/"+serverNode, false, null);
				if(data.length>0)
				{
					try {
						interfaceMsgs.add(RemoteInterfaceInfoProtobuf.Message.parseFrom(data));
					} catch (InvalidProtocolBufferException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return interfaceMsgs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.rpc.interfaces.IRemoteCallService#regiesterServiceToESB
	 * (com.bigflag.toolkit.rpc.beans.RemoteInterfaceInfoProtobuf)
	 */
	@Override
	public boolean regiesterServiceToESB(RemoteInterfaceInfoProtobuf.Message interfaceInfo) {
		this.checkInit();
		String interfaceName = interfaceInfo.getInterfaceFullName();
		if(!zookeeperService.existNode(esbServiceRootPath, false, null))
		{
			zookeeperService.createPersistentPath(esbServiceRootPath, DateTime.now().toString().getBytes(), false);
		}
		if(!zookeeperService.existNode((esbServiceRootPath+"/"+interfaceName).intern(), false, null))
		{
			zookeeperService.createPersistentPath((esbServiceRootPath+"/"+interfaceName).intern(), DateTime.now().toString().getBytes(), false);
		}
		String serviceProvider=interfaceInfo.getServiceProvider();
		String serviceNode=esbServiceRootPath+"/"+interfaceName+"/"+serviceProvider;
		if(zookeeperService.existNode(serviceNode, false, null))
		{
			zookeeperService.removePath(serviceNode);
		}
		return zookeeperService.createEphemeralPath(serviceNode, interfaceInfo.toByteArray(), false);
	}

}
