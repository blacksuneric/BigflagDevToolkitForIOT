/**
 * 
 */
package com.bigflag.toolkit.tool.socket.impl.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.bigflag.toolkit.tool.socket.interfaces.ISocketDataReceiver;
import com.bigflag.toolkit.tool.socket.interfaces.ISocketService;
import com.bigflag.toolkit.tool.socket.interfaces.ISocketSession;

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
 *         Create at:2017年9月26日 下午1:28:16
 */
public class SocketSerivceMinaImpl implements ISocketService {

	private NioSocketAcceptor acceptor;
	private MinaTCPHandler minaTCPHandler=new MinaTCPHandler();

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#startToListenTCP(int)
	 */
	@Override
	public boolean startToListenTCP(int port) {
		acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MinaProtocolCodecFactory()));

		acceptor.setHandler(minaTCPHandler);
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 1800);
		acceptor.setCloseOnDeactivation(true);
		try {
			acceptor.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#stopListenTCP()
	 */
	@Override
	public boolean stopListenTCP() {
		acceptor.unbind();
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#registerSocketDataReceiver(com.bigflag.toolkit.tool.socket.interfaces.ISocketDataReceiver)
	 */
	@Override
	public boolean registerSocketDataReceiver(ISocketDataReceiver socketDataReceiver) {
		return minaTCPHandler.addISocketDataReceiver(socketDataReceiver);
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#findSocketSession(long)
	 */
	@Override
	public ISocketSession findSocketSession(long sessionID) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#findSocketSession(java.lang.String)
	 */
	@Override
	public ISocketSession findSocketSession(String sessionKey) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#broadcastDataToAllSession(byte[])
	 */
	@Override
	public boolean broadcastDataToAllSession(byte[] data) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#getAllSocketSessions()
	 */
	@Override
	public List<ISocketSession> getAllSocketSessions() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#closeSession(long)
	 */
	@Override
	public boolean closeSession(long sessionID) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#removeISocketDataReceiver(com.bigflag.toolkit.tool.socket.interfaces.ISocketDataReceiver)
	 */
	@Override
	public boolean removeISocketDataReceiver(ISocketDataReceiver socketDataReceiver) {
		return minaTCPHandler.removeISocketDataReceiver(socketDataReceiver);
	}

}
