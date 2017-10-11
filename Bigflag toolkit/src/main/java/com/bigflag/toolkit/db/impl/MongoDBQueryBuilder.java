/**
 * 
 */
package com.bigflag.toolkit.db.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.bigflag.toolkit.db.interfaces.IMongoDBQueryBuilder;

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
 *         Create at:2017年10月12日 上午10:44:33
 */
public class MongoDBQueryBuilder implements IMongoDBQueryBuilder{

	private Map<String,Object> parameters=new HashMap<String, Object>();
	
	public MongoDBQueryBuilder append(String key,Object value)
	{
		this.parameters.put(key, value);
		return this;
	}
	
	
	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.db.interfaces.IMongoDBQueryBuilder#toJson()
	 */
	@Override
	public String buildJson() {
		return JSON.toJSONString(parameters);
	}

}
