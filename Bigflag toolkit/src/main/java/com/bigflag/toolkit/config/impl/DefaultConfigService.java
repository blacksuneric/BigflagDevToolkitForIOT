/**
 * 
 */
package com.bigflag.toolkit.config.impl;

import com.bigflag.toolkit.config.interfaces.IConfigService;

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
 *         Create at: 2017年10月21日 下午2:57:17 
 */
public class DefaultConfigService implements IConfigService {

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.config.interfaces.IConfigService#initConfigService()
	 */
	@Override
	public boolean initConfigService() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.config.interfaces.IConfigService#watchConfig(java.lang.String, com.bigflag.toolkit.config.interfaces.IConfigService.IHandleConfigValueChanged)
	 */
	@Override
	public boolean watchConfig(String configKey, IHandleConfigValueChanged handleConfigValueChanged) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.config.interfaces.IConfigService#queryConfigContent(java.lang.String)
	 */
	@Override
	public String queryConfigContent(String configKey) {
		// TODO Auto-generated method stub
		return null;
	}

}
