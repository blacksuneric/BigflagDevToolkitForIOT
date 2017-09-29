/**
 * 
 */
package com.bigflag.toolkit.tool.socket.impl.mina;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService;

/***
 * 
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
 *         Create at:2017年9月26日 下午1:28:16
 */
public class SocketUDPSerivceMinaImpl implements ISocketUDPService {

	private NioSocketAcceptor acceptor;
	
	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService#startToListenUDP(int, com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService.OnReceiveData, com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService.OnSessionCreated, com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService.OnSessionClosed)
	 */
	@Override
	public boolean startToListenUDP(int port, OnReceiveData onReceiveData, OnSessionCreated onSessionCreated, OnSessionClosed onSessionClosed) {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService#stopListenUDP()
	 */
	@Override
	public boolean stopListenUDP() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
