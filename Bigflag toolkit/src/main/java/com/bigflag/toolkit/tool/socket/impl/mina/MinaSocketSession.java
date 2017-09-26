/**
 * 
 */
package com.bigflag.toolkit.tool.socket.impl.mina;

import java.util.Set;

import org.apache.mina.core.session.IoSession;

import com.bigflag.toolkit.tool.socket.interfaces.ISocketSession;

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
 *         Create at:2017年9月26日 下午8:40:04
 */
public final class MinaSocketSession implements ISocketSession {

	private final IoSession session;
	
	/**
	 * @param session
	 */
	public MinaSocketSession(IoSession session) {
		super();
		this.session = session;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketSession#sendData(byte[])
	 */
	@Override
	public boolean sendData(byte[] data) {
		this.session.write(data);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketSession#closeSession()
	 */
	@Override
	public boolean closeSession() {
		this.session.closeOnFlush();
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketSession#getSessionID()
	 */
	@Override
	public long getSessionID() {
		// TODO Auto-generated method stub
		return this.session.getId();
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketSession#getSessionIdentifyKey()
	 */
	@Override
	public String getSessionIdentifyKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketSession#saveSessionAttribute(java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean saveSessionAttribute(String key, Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketSession#getSessionAttribute(java.lang.String)
	 */
	@Override
	public Object getSessionAttribute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketSession#removeSessionAttribute(java.lang.String)
	 */
	@Override
	public boolean removeSessionAttribute(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketSession#clearSessionAttribute()
	 */
	@Override
	public boolean clearSessionAttribute() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.socket.interfaces.ISocketSession#getSessionAttributeKeySet()
	 */
	@Override
	public Set<String> getSessionAttributeKeySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
