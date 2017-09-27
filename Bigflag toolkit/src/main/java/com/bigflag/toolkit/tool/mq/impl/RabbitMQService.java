/**
 * 
 */
package com.bigflag.toolkit.tool.mq.impl;

import com.bigflag.toolkit.tool.mq.beans.BaseMQConfigBean;
import com.bigflag.toolkit.tool.mq.interfaces.IMQToolService;

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

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.mq.interfaces.IMQToolService#isInit()
	 */
	@Override
	public boolean isInit() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.mq.interfaces.IMQToolService#connectServer(com.bigflag.toolkit.tool.mq.beans.BaseMQConfigBean)
	 */
	@Override
	public boolean connectServer(BaseMQConfigBean configBean) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.mq.interfaces.IMQToolService#registerInterestingEvent(java.lang.String)
	 */
	@Override
	public boolean registerInterestingEvent(String eventKey) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.mq.interfaces.IMQToolService#sendMsg(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean sendMsg(String routineKey, String msg) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.mq.interfaces.IMQToolService#processMsg(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean processMsg(String eventKey, String msg) {
		// TODO Auto-generated method stub
		return false;
	}

}
