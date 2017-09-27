/**
 * 
 */
package com.bigflag.toolkit.tool.mq.impl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

import com.bigflag.toolkit.exception.MQServiceNotInitException;
import com.bigflag.toolkit.tool.mq.beans.BaseMQConfigBean;
import com.bigflag.toolkit.tool.mq.enums.MQSendType;
import com.bigflag.toolkit.tool.mq.interfaces.IMQToolService;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

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
 *         Create at: 2017年9月27日 下午7:13:15 
 */
public class RabbitMQService implements IMQToolService {
	private Map<String,Channel> sendChannels=new ConcurrentHashMap<String, Channel>();
	private Map<String,Channel> receiveChannels=new ConcurrentHashMap<String, Channel>();
	private ConnectionFactory factory;
	private Connection connection;
	private boolean isInit;
	private void checkServiceInit()
	{
		if(!isInit())
		{
			throw new MQServiceNotInitException();
		}
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.mq.interfaces.IMQToolService#isInit()
	 */
	@Override
	public boolean isInit() {
		return isInit;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.mq.interfaces.IMQToolService#connectServer(com.bigflag.toolkit.tool.mq.beans.BaseMQConfigBean)
	 */
	@Override
	public boolean connectServer(BaseMQConfigBean configBean) {
		factory = new ConnectionFactory();
		factory.setUsername(configBean.getUserName());
		factory.setPassword(configBean.getPwd());
		factory.setHost(configBean.getServerAddress());
		factory.setPort(configBean.getPort());
		factory.setAutomaticRecoveryEnabled(true);
		try {
			connection=factory.newConnection();
			isInit=true;
			return true;
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isInit=false;
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.mq.interfaces.IMQToolService#registerInterestingSubject(java.lang.String, java.lang.String, com.bigflag.toolkit.tool.mq.interfaces.IMQToolService.OnReceiveMsg)
	 */
	@Override
	public boolean registerInterestingSubject(String subject, String routingKey, OnReceiveMsg onReceiveMsg) throws IOException {
		this.checkServiceInit();
		Channel channel=this.receiveChannels.get((subject+routingKey).intern());
		if(channel==null)
		{
			channel=connection.createChannel();
			DeclareOk queueOk = channel.queueDeclare();
			channel.queueBind(queueOk.getQueue(), subject, routingKey);
			channel.basicConsume(queueOk.getQueue(), new Consumer() {
				
				@Override
				public void handleShutdownSignal(String arg0, ShutdownSignalException arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void handleRecoverOk(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void handleDelivery(String arg0, Envelope arg1, BasicProperties arg2, byte[] msg) throws IOException {
					if(onReceiveMsg!=null)
					{
						onReceiveMsg.onReceiveMsg(msg);
					}
				}
				
				@Override
				public void handleConsumeOk(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void handleCancelOk(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void handleCancel(String arg0) throws IOException {
					// TODO Auto-generated method stub
					
				}
			});
			this.receiveChannels.put((subject+routingKey).intern(),channel);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.mq.interfaces.IMQToolService#sendMsg(java.lang.String, java.lang.String, byte[])
	 */
	@Override
	public boolean sendMsg(String subject, String routingKey, byte[] msg,MQSendType mqSendType) throws IOException {
		this.checkServiceInit();
		Channel channel=this.sendChannels.get((subject+routingKey).intern());
		if(channel==null)
		{
			channel=connection.createChannel();
			switch(mqSendType){
				case DIRECT:
					channel.exchangeDeclare(subject, BuiltinExchangeType.DIRECT);
					break;
				case TOPICE:
					channel.exchangeDeclare(subject, BuiltinExchangeType.TOPIC);
					break;
				case BROADCAST:
					channel.exchangeDeclare(subject, BuiltinExchangeType.FANOUT);
					break;
			}
			this.sendChannels.put((subject+routingKey).intern(),channel);
		}
		channel.basicPublish(subject, routingKey, null, msg);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.mq.interfaces.IMQToolService#closeConnection()
	 */
	@Override
	public boolean closeConnection() {
		// TODO Auto-generated method stub
		return false;
	}

}
