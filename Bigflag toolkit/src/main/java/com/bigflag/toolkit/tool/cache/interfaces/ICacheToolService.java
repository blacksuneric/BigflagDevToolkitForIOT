/**
 * 
 */
package com.bigflag.toolkit.tool.cache.interfaces;

import java.util.List;

import com.bigflag.toolkit.tool.cache.beans.BaseCacheConfigBean;

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
 *         Create at:2017年9月20日 下午8:36:57
 */
public interface ICacheToolService {
	public static final int EXPIRE_TIME_NOT_EXPIRE=-1;
	
	public boolean initCacheService(BaseCacheConfigBean configBean);
	public boolean isInited();
	public String findValueByKey(String key);
	public <T> T findObjByKey(String key,Class<T> clazz);
	public boolean updateOrSaveValueByKey(String key,String value,int expireTime);
	public boolean updateOrSaveValueByKey(String key,Object value,int expireTime);
	public boolean removeValue(String key);
	public <T> List<T> findObjsByKey(String key,Class<T> clazz);
	public boolean insertValue(String containterName,String key,String value);
	public boolean insertValue(String containterName,String key,Object value);
}
