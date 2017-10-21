/**
 * 
 */
package com.bigflag.toolkit.tool.script.impl;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.bigflag.toolkit.tool.script.interfaces.IScriptService;

/**
 * Copyright 2017-2027 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * @author Eric,Liu<br>
 *         mail: 34223022@qq.com<br>
 *         Create at: 2017年10月21日 下午2:56:57
 */
public class DefaultScriptService implements IScriptService {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.script.interfaces.IScriptService#executeScript
	 * (java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object executeScript(String functionName, String scriptContent, Object... inputValues) throws ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("nashorn");
		if (engine == null) {
			engine = manager.getEngineByName("javascript");
		}
		if (inputValues != null) {
			for (int i = 0; i < inputValues.length; i++) {
				engine.put("param" + i, inputValues[i]);
			}
		}
		engine.eval(scriptContent);
		return ((Invocable) manager).invokeFunction(functionName);
	}
}
