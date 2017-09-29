/**
 * 
 */
package com.bigflag.toolkit.tool.socket.impl.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

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

	private NioDatagramAcceptor acceptor;
	
	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService#startToListenUDP(int, com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService.OnReceiveData, com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService.OnSessionCreated, com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService.OnSessionClosed)
	 */
	@Override
	public boolean startToListenUDP(int port, OnReceiveData onReceiveData, OnSessionCreated onSessionCreated, OnSessionClosed onSessionClosed) {
		acceptor = new NioDatagramAcceptor();

		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MinaProtocolCodecFactory()));
		acceptor.setHandler(new MinaUDPHandler(onReceiveData, onSessionCreated, onSessionClosed));
		DatagramSessionConfig dcfg = (DatagramSessionConfig) acceptor.getSessionConfig();
		dcfg.setReuseAddress(true);
		dcfg.setReadBufferSize(2048);
		dcfg.setIdleTime(IdleStatus.BOTH_IDLE, 1800);
		acceptor.setCloseOnDeactivation(true);
		try {
			acceptor.bind(new InetSocketAddress(port));
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketUDPService#stopListenUDP()
	 */
	@Override
	public boolean stopListenUDP() {
		acceptor.unbind();
		return true;
	}
	

}
