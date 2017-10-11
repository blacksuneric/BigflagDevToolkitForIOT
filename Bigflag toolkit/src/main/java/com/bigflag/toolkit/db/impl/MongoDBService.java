/**
 * 
 */
package com.bigflag.toolkit.db.impl;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigflag.toolkit.db.beans.BaseDBBean;
import com.bigflag.toolkit.db.interfaces.IMongoDBData;
import com.bigflag.toolkit.db.interfaces.IMongoDBQueryBuilder;
import com.bigflag.toolkit.db.interfaces.IMongoDBService;
import com.bigflag.toolkit.exception.MongoDBServiceNotInitException;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertOneOptions;

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
	private MongoClient mc;
	private String databaseName;
	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.db.interfaces.IMongoDBService#connectMongoDB(java.lang.String)
	 */
	@Override
	public boolean connectMongoDB(String connectURL,String databaseName) {
		mc=new MongoClient(new MongoClientURI(connectURL
				,MongoClientOptions.builder().readPreference(ReadPreference.secondaryPreferred())));
		this.databaseName=databaseName;
		return true;
	}
	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.db.interfaces.IMongoDBService#upsertOne(com.bigflag.toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public boolean upsertOne(IMongoDBData dbBean) {
		this.isInit();
		String collectionName=dbBean.retrieveCollectionName();
		MongoDatabase db= mc.getDatabase(databaseName);
		MongoCollection<Document> collection=db.getCollection(collectionName);
		collection.insertOne(Document.parse(JSON.toJSONString(dbBean)));
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.db.interfaces.IMongoDBService#update(com.bigflag.toolkit.db.interfaces.IMongoDBQueryBuilder, com.bigflag.toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public boolean update(IMongoDBQueryBuilder query, IMongoDBData dbBean) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.db.interfaces.IMongoDBService#findOne(com.bigflag.toolkit.db.interfaces.IMongoDBQueryBuilder, java.lang.Class)
	 */
	@Override
	public <T extends IMongoDBData> T findOne(IMongoDBQueryBuilder query, Class<T> clazz) {
		this.isInit();
		IMongoDBData bean;
		try {
			bean = (IMongoDBData)clazz.newInstance();
			String collectionName=bean.retrieveCollectionName();
			MongoDatabase db= mc.getDatabase(databaseName);
			MongoCollection<Document> collection=db.getCollection(collectionName);
			FindIterable<Document> result=collection.find(Document.parse(query.buildJson()));
			Document first=result.first();
			if(first==null)
			{
				return null;
			}else
			{
				return JSON.parseObject(refineJSONString(first.toJson()), clazz);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public boolean remove(IMongoDBQueryBuilder query, IMongoDBData dbBean) {
		// TODO Auto-generated method stub
		return false;
	}

	private void isInit()
	{
		if(mc==null)
		{
			throw new MongoDBServiceNotInitException();
		}
	}
	
	
	private String refineJSONString(String mongoJson)
	{
		while(mongoJson.contains("$"))
		{
			int index=mongoJson.indexOf("{ \"$");
			String block=mongoJson.substring(index,mongoJson.indexOf("}", index)+1).intern();
			index=block.indexOf(": ");
			String value=block.substring(index+": ".length(), block.indexOf(" }")).intern();
			mongoJson=mongoJson.replace(block, value).intern();
		}
		return mongoJson;
	}

}
