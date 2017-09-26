package com.bigflag.toolkit.tool.socket.impl.mina;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.bigflag.toolkit.tool.socket.interfaces.ISocketService;
import com.bigflag.toolkit.tool.socket.interfaces.ISocketService.OnReceiveData;
import com.bigflag.toolkit.tool.socket.interfaces.ISocketService.OnSessionClosed;
import com.bigflag.toolkit.tool.socket.interfaces.ISocketService.OnSessionCreated;

public class MinaTCPHandler extends IoHandlerAdapter implements IoHandler {
	
	private ISocketService.OnReceiveData onReceiveDataListener;
	private ISocketService.OnSessionCreated onSessionCreated;
	private ISocketService.OnSessionClosed onSessionClosed;
	
	/**
	 * @param onReceiveDataListener
	 */
	public MinaTCPHandler(OnReceiveData onReceiveDataListener,OnSessionCreated onSessionCreated,OnSessionClosed onSessionClosed) {
		super();
		this.onReceiveDataListener = onReceiveDataListener;
		this.onSessionCreated=onSessionCreated;
		this.onSessionClosed=onSessionClosed;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
		cause.printStackTrace();
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.inputClosed(session);
	}

	@Override
	public void messageReceived(IoSession session, final Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageReceived(session, message);
		if(onReceiveDataListener!=null)
		{
			onReceiveDataListener.onReceiveData(message);
		}
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
		if(onSessionClosed!=null)
		{
			onSessionClosed.onSessionClosed(new MinaSocketSession(session));
		}
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
		if(onSessionCreated!=null)
		{
			onSessionCreated.onSessionCreated(new MinaSocketSession(session));
		}
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

}
