/**
 * 
 */
package com.bigflag.toolkit.config.interfaces;

import com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService;

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
 *         Create at: 2017年10月21日 下午2:51:23 
 */
public interface IConfigService {
	public boolean initConfigService(ICoordinatorToolService coordinatorToolService);
	public boolean watchConfig(String configKey,IHandleConfigValueChanged handleConfigValueChanged);
	public String queryConfigContent(String configKey);
	
	public interface IHandleConfigValueChanged{
		public void processConfigValueChanged(String configContent);
	}
}
