package com.bigflag.toolkit.tool.socket.impl.mina;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.bigflag.toolkit.tool.socket.interfaces.ISocketDataReceiver;

public class MinaTCPHandler extends IoHandlerAdapter implements IoHandler {
	private Set<ISocketDataReceiver> socketDataReceivers=new CopyOnWriteArraySet<ISocketDataReceiver>();
	
	public boolean addISocketDataReceiver(ISocketDataReceiver socketDataReceiver)
	{
		return this.socketDataReceivers.add(socketDataReceiver);
	}
	
	public boolean removeISocketDataReceiver(ISocketDataReceiver socketDataReceiver)
	{
		return this.socketDataReceivers.remove(socketDataReceiver);
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
		for(ISocketDataReceiver socketDataReceiver:socketDataReceivers)
		{
			socketDataReceiver.handleData((byte[])message);
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

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
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
