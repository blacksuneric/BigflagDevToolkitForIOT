/**
 * 
 */
package com.bigflag.toolkit.db.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigflag.toolkit.db.beans.BaseDBBean;
import com.bigflag.toolkit.db.interfaces.IMongoDBQueryBuilder;
import com.bigflag.toolkit.db.interfaces.IMongoDBService;
import com.bigflag.toolkit.exception.MongoDBServiceNotInitException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

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
 *         Create at: 2017年10月9日 下午10:07:36
 */
public class MongoDBService implements IMongoDBService {
	private MongoClient mc;
	private String databaseName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IMongoDBService#connectMongoDB(java
	 * .lang.String)
	 */
	@Override
	public boolean connectMongoDB(String connectURL, String databaseName) {
		mc = new MongoClient(new MongoClientURI(connectURL, MongoClientOptions.builder().readPreference(ReadPreference.secondaryPreferred())));
		this.databaseName = databaseName;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IMongoDBService#upsertOne(com.bigflag
	 * .toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public String upsertOne(BaseDBBean dbBean) {
		this.isInit();
		String collectionName = dbBean.retrieveCollectionName();
		MongoDatabase db = mc.getDatabase(databaseName);
		MongoCollection<Document> collection = db.getCollection(collectionName);
		JSONObject jo = (JSONObject) JSON.toJSON(dbBean);
		BsonValue  upsertedID = collection
				.replaceOne(new Document().append("_id", new ObjectId(dbBean.getUuid())), Document.parse(jo.toJSONString()),
						new UpdateOptions().upsert(true)).getUpsertedId();
		return upsertedID==null?"":upsertedID.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IMongoDBService#update(com.bigflag.
	 * toolkit.db.interfaces.IMongoDBQueryBuilder,
	 * com.bigflag.toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public boolean update(IMongoDBQueryBuilder query, BaseDBBean dbBean) {
		this.isInit();
		String collectionName = dbBean.retrieveCollectionName();
		MongoDatabase db = mc.getDatabase(databaseName);
		MongoCollection<Document> collection = db.getCollection(collectionName);
		JSONObject jo = (JSONObject) JSON.toJSON(dbBean);
		collection.replaceOne(Document.parse(jo.toJSONString()), Document.parse(jo.toJSONString()));
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IMongoDBService#findOne(com.bigflag
	 * .toolkit.db.interfaces.IMongoDBQueryBuilder, java.lang.Class)
	 */
	@Override
	public <T extends BaseDBBean> T findOne(IMongoDBQueryBuilder query, Class<T> clazz) {
		this.isInit();
		BaseDBBean bean;
		try {
			bean = (BaseDBBean) clazz.newInstance();
			String collectionName = bean.retrieveCollectionName();
			MongoDatabase db = mc.getDatabase(databaseName);
			MongoCollection<Document> collection = db.getCollection(collectionName);
			FindIterable<Document> result = collection.find(Document.parse(query.buildJson()));
			Document first = result.first();
			if (first == null) {
				return null;
			} else {
				String json = refineJSONString(first.toJson());
				JSONObject jo = JSONObject.parseObject(json);
				BaseDBBean obj = (BaseDBBean) JSON.parseObject(json, clazz);
				obj.setUuid(jo.getString("_id"));
				return (T) obj;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IMongoDBService#findMany(com.bigflag
	 * .toolkit.db.interfaces.IMongoDBQueryBuilder, java.lang.Class)
	 */
	@Override
	public <T extends BaseDBBean> List<T> findMany(IMongoDBQueryBuilder query, Class<T> clazz) {
		this.isInit();
		BaseDBBean bean;
		try {
			bean = (BaseDBBean) clazz.newInstance();
			String collectionName = bean.retrieveCollectionName();
			MongoDatabase db = mc.getDatabase(databaseName);
			MongoCollection<Document> collection = db.getCollection(collectionName);
			FindIterable<Document> result = collection.find(Document.parse(query.buildJson()));
			List<T> beans = new ArrayList<T>();

			result.forEach(new Consumer<Document>() {

				@Override
				public void accept(Document doc) {
					String json = refineJSONString(doc.toJson());
					JSONObject jo = JSONObject.parseObject(json);
					BaseDBBean obj = (BaseDBBean) JSON.parseObject(json, clazz);
					obj.setUuid(jo.getString("_id"));
					beans.add((T) obj);
				}
			});
			return beans;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IMongoDBService#remove(com.bigflag.
	 * toolkit.db.interfaces.IMongoDBQueryBuilder,
	 * com.bigflag.toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public <T extends BaseDBBean> long remove(IMongoDBQueryBuilder query, Class<T> clazz) {
		this.isInit();
		BaseDBBean bean;
		try {
			bean=(BaseDBBean)clazz.newInstance();
			String collectionName = bean.retrieveCollectionName();
			MongoDatabase db = mc.getDatabase(databaseName);
			MongoCollection<Document> collection = db.getCollection(collectionName);
			return collection.deleteMany(Document.parse(query.toString())).getDeletedCount();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	private void isInit() {
		if (mc == null) {
			throw new MongoDBServiceNotInitException();
		}
	}

	private String refineJSONString(String mongoJson) {
		while (mongoJson.contains("$")) {
			int index = mongoJson.indexOf("{ \"$");
			String block = mongoJson.substring(index, mongoJson.indexOf("}", index) + 1).intern();
			index = block.indexOf(": ");
			String value = block.substring(index + ": ".length(), block.indexOf(" }")).intern();
			mongoJson = mongoJson.replace(block, value).intern();
		}
		return mongoJson;
	}

}
