/**
 * 
 */
package com.bigflag.toolkit.iot.devicehandler;

import com.bigflag.toolkit.iot.interfaces.IIOTDeviceIdentifier;
import com.bigflag.toolkit.iot.interfaces.IIOTDeviceProcessor;

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
 *         Create at: 2017年9月21日 下午6:23:51
 */
public abstract class AbstractIOTDeviceProcessor {
	private final IIOTDeviceIdentifier deviceIdentifier;
	private final IIOTDeviceProcessor  deviceProcessor;
	/**
	 * @param deviceIdentifier
	 * @param deviceProcessor
	 */
	public AbstractIOTDeviceProcessor(IIOTDeviceIdentifier deviceIdentifier, IIOTDeviceProcessor deviceProcessor) {
		super();
		this.deviceIdentifier = deviceIdentifier;
		this.deviceProcessor = deviceProcessor;
	}
	
	public void processIOTData(long sessionID,byte[] data)
	{
		if(this.deviceIdentifier.isThisDevice(data))
		{
			this.deviceProcessor.processIOTData(sessionID,data);
		}
	}
	
	
}
