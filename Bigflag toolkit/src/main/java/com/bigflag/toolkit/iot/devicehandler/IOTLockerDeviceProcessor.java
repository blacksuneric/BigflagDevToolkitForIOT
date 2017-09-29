/**
 * 
 */
package com.bigflag.toolkit.iot.devicehandler;

import java.util.Arrays;

import com.bigflag.toolkit.iot.interfaces.IIOTDeviceProcessor;
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
 *         Create at: 2017年9月21日 下午7:32:28 
 */
public class IOTLockerDeviceProcessor implements IIOTDeviceProcessor {

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.iot.interfaces.IIOTDeviceProcessor#processIOTData(byte[])
	 */
	public boolean processIOTData(long sessionID,byte[] data) {
		System.out.println("handled locker:"+Arrays.toString(data));
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.iot.interfaces.IIOTDeviceProcessor#processIOTUDPData(com.bigflag.toolkit.tool.socket.interfaces.ISocketSession, byte[])
	 */
	@Override
	public boolean processIOTUDPData(ISocketSession socketSession, byte[] data) {
		// TODO Auto-generated method stub
		return false;
	}

}
