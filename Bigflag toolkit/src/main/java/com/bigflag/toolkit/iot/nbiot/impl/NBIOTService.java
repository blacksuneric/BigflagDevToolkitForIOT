/**
 * 
 */
package com.bigflag.toolkit.iot.nbiot.impl;

import com.bigflag.toolkit.iot.nbiot.interfaces.INBIOTService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
 *         Create at:2017年9月29日 上午11:47:37
 */
public class NBIOTService implements INBIOTService {
	private Map<String,byte[]> nbiotSendDataCache=new ConcurrentHashMap<String, byte[]>();
	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.iot.nbiot.interfaces.INBIOTService#cacheNBIOTData(java.lang.String, byte[])
	 */
	@Override
	public boolean cacheNBIOTData(String key, byte[] data) {
		nbiotSendDataCache.put(key, data);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.iot.nbiot.interfaces.INBIOTService#getNBIOTCacheData(java.lang.String, byte[])
	 */
	@Override
	public byte[] getNBIOTCacheData(String key, byte[] data) {
		return nbiotSendDataCache.remove(key);
	}

}
