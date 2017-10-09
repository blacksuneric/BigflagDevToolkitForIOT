/**
 * 
 */
package com.bigflag.toolkit.db.impl;

import java.util.List;

import com.bigflag.toolkit.db.beans.BaseDBBean;
import com.bigflag.toolkit.db.interfaces.IMongoDBQueryBuilder;
import com.bigflag.toolkit.db.interfaces.IMongoDBService;
import com.mongodb.Mongo;

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
 *         Create at: 2017年10月9日 下午10:07:36 
 */
public class MongoDBService implements IMongoDBService {
	private Mongo m;
	
	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.db.interfaces.IMongoDBService#connectMongoDB(java.util.List, java.lang.String)
	 */
	@Override
	public boolean connectMongoDB(List<String> hosts, String dbName) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.db.interfaces.IMongoDBService#upsertOne(com.bigflag.toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public boolean upsertOne(BaseDBBean dbBean) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.db.interfaces.IMongoDBService#update(com.bigflag.toolkit.db.interfaces.IMongoDBQueryBuilder, com.bigflag.toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public boolean update(IMongoDBQueryBuilder query, BaseDBBean dbBean) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.db.interfaces.IMongoDBService#findOne(com.bigflag.toolkit.db.interfaces.IMongoDBQueryBuilder, java.lang.Class)
	 */
	@Override
	public <T> T findOne(IMongoDBQueryBuilder query, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.db.interfaces.IMongoDBService#findMany(com.bigflag.toolkit.db.interfaces.IMongoDBQueryBuilder, java.lang.Class)
	 */
	@Override
	public <T> List<T> findMany(IMongoDBQueryBuilder query, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.db.interfaces.IMongoDBService#remove(com.bigflag.toolkit.db.interfaces.IMongoDBQueryBuilder, com.bigflag.toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public boolean remove(IMongoDBQueryBuilder query, BaseDBBean dbBean) {
		// TODO Auto-generated method stub
		return false;
	}

}
