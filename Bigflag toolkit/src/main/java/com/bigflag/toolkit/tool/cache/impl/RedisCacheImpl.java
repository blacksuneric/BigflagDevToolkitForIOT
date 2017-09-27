/**
 * 
 */
package com.bigflag.toolkit.tool.cache.impl;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSON;
import com.bigflag.toolkit.exception.CacheServiceNotInitException;
import com.bigflag.toolkit.tool.cache.beans.BaseCacheConfigBean;
import com.bigflag.toolkit.tool.cache.interfaces.ICacheToolService;

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
 *         Create at:2017年9月20日 下午8:38:37
 */
public class RedisCacheImpl implements ICacheToolService {

	private JedisPool jedisPool = null;
	private static int MAX_IDLE = 200;
	private static int MAX_WAIT = 10000;
	private static boolean TEST_ON_BORROW = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.cache.ICacheService#initCacheService(com.bigflag
	 * .toolkit.tool.cache.beans.BaseCacheConfigBean)
	 */
	public boolean initCacheService(BaseCacheConfigBean configBean) {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(MAX_IDLE);
			config.setTestOnBorrow(TEST_ON_BORROW);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setMaxTotal(-1);
			jedisPool = new JedisPool(config, configBean.getServerHost(), configBean.getPort());
			return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bigflag.toolkit.tool.cache.ICacheService#isInited()
	 */
	public boolean isInited() {
		return jedisPool != null;
	}

	private void checkIfInit()
	{
		if(!isInited())
		{
			throw new CacheServiceNotInitException();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.cache.ICacheService#findValueByKey(java.lang
	 * .String)
	 */
	public String findValueByKey(String key) {
		checkIfInit();
		Jedis jedis = jedisPool.getResource();
		String result=jedis.get(key);
		jedis.close();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.cache.ICacheService#findObjByKey(java.lang.String
	 * , java.lang.Class)
	 */
	public <T> T findObjByKey(String key, Class<T> clazz) {
		checkIfInit();
		String result=this.findValueByKey(key);
		return JSON.parseObject(result, clazz);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.cache.ICacheService#findObjsByKey(java.lang.
	 * String, java.lang.Class)
	 */
	public <T> List<T> findObjsByKey(String key, Class<T> clazz) {
		List<String> entries=this.getListValues(key);
		List<T> result=new ArrayList<T>();
		for(String entry:entries)
		{
			result.add(JSON.parseObject(entry, clazz));
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.cache.interfaces.ICacheToolService#saveValueByKey(java.lang.String, java.lang.String)
	 */
	@Override
	public String saveValueByKey(String key, String value) {
		checkIfInit();
		Jedis jedis = jedisPool.getResource();
		String result=jedis.set(key, value);
		jedis.close();
		return result;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.cache.interfaces.ICacheToolService#saveValueByKeyExpireTime(java.lang.String, java.lang.String, int)
	 */
	@Override
	public String saveValueByKeyExpireTime(String key, String value, int expireTime) {
		checkIfInit();
		Jedis jedis = jedisPool.getResource();
		String result=jedis.setex(key, expireTime, value);
		jedis.close();
		return result;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.cache.interfaces.ICacheToolService#removeValue(java.lang.String)
	 */
	@Override
	public String removeValue(String key) {
		checkIfInit();
		Jedis jedis = jedisPool.getResource();
		String result=jedis.get(key);
		jedis.del(key);
		jedis.close();
		return result;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.cache.interfaces.ICacheToolService#insertValue(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean insertValue(String listName, String value) {
		Jedis jedis=jedisPool.getResource();
		long result=jedis.lpush(listName, value);
		jedis.close();
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.cache.interfaces.ICacheToolService#removeListValue(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean removeListValue(String listName, String value) {
		Jedis jedis=jedisPool.getResource();
		long result=jedis.lrem(listName, 0, value);
		jedis.close();
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.cache.interfaces.ICacheToolService#getListValues(java.lang.String)
	 */
	@Override
	public List<String> getListValues(String listName) {
		Jedis jedis=jedisPool.getResource();
		List<String> result=jedis.lrange(listName, 0, -1);
		jedis.close();
		return result;
	}

	

	

}
