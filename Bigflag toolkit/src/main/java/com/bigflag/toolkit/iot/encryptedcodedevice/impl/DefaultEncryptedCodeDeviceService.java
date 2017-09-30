/**
 * 
 */
package com.bigflag.toolkit.iot.encryptedcodedevice.impl;

import java.util.Date;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;

import com.bigflag.toolkit.iot.encryptedcodedevice.interfaces.IEncryptedCodeDeviceService;

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
 *         Create at:2017年9月30日 下午6:50:36
 */
public class DefaultEncryptedCodeDeviceService implements IEncryptedCodeDeviceService {

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.iot.encryptedcodedevice.interfaces.IEncryptedCodeDeviceService#getEncryptedCodeForDevice(java.util.Date, java.lang.String)
	 */
	@Override
	public String getEncryptedCodeForDevice(Date time, String deviceCode) {
		int deviceNumber=NumberUtils.toInt(deviceCode, 0);
		int key=RandomUtils.nextInt(10, 99);
		if(deviceNumber<0||deviceNumber>10000000)
		{
			throw new IllegalArgumentException("the default encrypted code device's deviceCode should be number 1 ~ 9999999");
		}
		String timeStr=DateTime.now().toString("MMddHHmm");
		int code=(NumberUtils.toInt(timeStr)+deviceNumber)^key;
		return key+String.valueOf(code);
	}

}
