/**
 * 
 */
package com.bigflag.toolkit.iot.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.bigflag.toolkit.iot.devicehandler.AbstractIOTDeviceProcessor;
import com.bigflag.toolkit.iot.interfaces.IIOTHandlerCenter;

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
 *         Create at: 2017年9月21日 下午8:04:33 
 */
public class DefaultIOTHandlerCenter implements IIOTHandlerCenter {

	private List<AbstractIOTDeviceProcessor> deviceProcessors=new CopyOnWriteArrayList<AbstractIOTDeviceProcessor>();
	private Executor handlerExecutor=Executors.newCachedThreadPool();
	
	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.iot.interfaces.IIOTHandlerCenter#registerIOTProcessor(com.bigflag.toolkit.iot.devicehandler.AbstractIOTDeviceProcessor)
	 */
	public boolean registerIOTProcessor(AbstractIOTDeviceProcessor iotDeviceProcessor) {
		return deviceProcessors.add(iotDeviceProcessor);
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.iot.interfaces.IIOTHandlerCenter#processIOTData(byte[])
	 */
	public void processIOTData(long sessionID,byte[] data) {
		for(AbstractIOTDeviceProcessor deviceProcessor:deviceProcessors)
		{
			handlerExecutor.execute(()->{
				deviceProcessor.processIOTData(sessionID,data);
			});
		}
	}

}
