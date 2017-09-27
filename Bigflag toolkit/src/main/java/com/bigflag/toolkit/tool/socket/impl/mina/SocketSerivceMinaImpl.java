/**
 * 
 */
package com.bigflag.toolkit.tool.socket.impl.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.bigflag.toolkit.tool.socket.interfaces.ISocketService;
import com.bigflag.toolkit.tool.socket.interfaces.ISocketService.OnSessionClosed;
import com.bigflag.toolkit.tool.socket.interfaces.ISocketService.OnSessionCreated;
import com.bigflag.toolkit.tool.socket.interfaces.ISocketSession;

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
public class SocketSerivceMinaImpl implements ISocketService,OnSessionCreated,OnSessionClosed {

	private NioSocketAcceptor acceptor;
	private Map<Long,ISocketSession> socketSessions=new ConcurrentHashMap<Long, ISocketSession>();
	private ISocketService.OnSessionCreated outsideOnSessionCreated;
	private ISocketService.OnSessionClosed outsideOnSessionClosed;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.socket.interfaces.ISocketService#startToListenTCP
	 * (int)
	 */
	@Override
	public boolean startToListenTCP(int port, ISocketService.OnReceiveData onReceiveData, ISocketService.OnSessionCreated onSessionCreated, ISocketService.OnSessionClosed onSessionClosed) {
		acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MinaProtocolCodecFactory()));
		this.outsideOnSessionClosed=onSessionClosed;
		this.outsideOnSessionCreated=onSessionCreated;
		acceptor.setHandler(new MinaTCPHandler(onReceiveData,this,this));
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 1800);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.socket.interfaces.ISocketService#stopListenTCP()
	 */
	@Override
	public boolean stopListenTCP() {
		acceptor.unbind();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.socket.interfaces.ISocketService#findSocketSession
	 * (long)
	 */
	@Override
	public ISocketSession findSocketSession(long sessionID) {
		return socketSessions.get(sessionID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.socket.interfaces.ISocketService#findSocketSession
	 * (java.lang.String)
	 */
	@Override
	public ISocketSession findSocketSession(String sessionKey) {
		throw new NotImplementedException("not implement currently");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#
	 * broadcastDataToAllSession(byte[])
	 */
	@Override
	public boolean broadcastDataToAllSession(byte[] data) {
		acceptor.broadcast(data);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#
	 * getAllSocketSessions()
	 */
	@Override
	public Collection<ISocketSession> getAllSocketSessions() {
		return socketSessions.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.socket.interfaces.ISocketService#closeSession
	 * (long)
	 */
	@Override
	public boolean closeSession(long sessionID) {
		ISocketSession session=this.findSocketSession(sessionID);
		if(session!=null)
		{
			socketSessions.remove(sessionID);
			return session.closeSession();
		}else
		{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#addSocketSession(com.bigflag.toolkit.tool.socket.interfaces.ISocketSession)
	 */
	@Override
	public boolean addSocketSession(ISocketSession socketSession) {
		socketSessions.put(socketSession.getSessionID(), socketSession);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService#removeSocketSession(com.bigflag.toolkit.tool.socket.interfaces.ISocketSession)
	 */
	@Override
	public boolean removeSocketSession(ISocketSession socketSession) {
		socketSessions.remove(socketSession.getSessionID());
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService.OnSessionClosed#onSessionClosed(com.bigflag.toolkit.tool.socket.interfaces.ISocketSession)
	 */
	@Override
	public void onSessionClosed(ISocketSession socketSession) {
		this.removeSocketSession(socketSession);
		if(outsideOnSessionClosed!=null)
		{
			outsideOnSessionClosed.onSessionClosed(socketSession);
		}
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketService.OnSessionCreated#onSessionCreated(com.bigflag.toolkit.tool.socket.interfaces.ISocketSession)
	 */
	@Override
	public void onSessionCreated(ISocketSession socketSession) {
		this.addSocketSession(socketSession);
		if(outsideOnSessionCreated!=null)
		{
			outsideOnSessionCreated.onSessionCreated(socketSession);
		}
	}

}
