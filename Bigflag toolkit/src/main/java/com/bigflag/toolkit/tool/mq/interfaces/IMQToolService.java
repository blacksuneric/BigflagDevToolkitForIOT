/**
 * 
 */
package com.bigflag.toolkit.tool.mq.interfaces;

import com.bigflag.toolkit.tool.mq.beans.BaseMQConfigBean;

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
 *         Create at:2017年9月21日 上午11:34:19
 */
public interface IMQToolService {
	public boolean isInit();
	public boolean connectServer(BaseMQConfigBean configBean);
	public boolean registerInterestingEvent(String eventKey);
	public boolean sendMsg(String routineKey,String msg);
	public boolean processMsg(String eventKey,String msg);
}
