/**
 * 
 */
package com.bigflag.toolkit.db.interfaces;

import java.util.List;

import com.bigflag.toolkit.db.beans.BaseDBBean;

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
 *         Create at: 2017年10月9日 下午9:16:12 
 */
public interface IMongoDBService {
	public boolean connectMongoDB(String connectURL,String databaseName);
	public boolean upsertOne(IMongoDBData dbBean);
	public boolean update(IMongoDBQueryBuilder query,IMongoDBData dbBean);
	public <T extends IMongoDBData> T findOne(IMongoDBQueryBuilder query,Class<T> clazz);
	public <T> List<T> findMany(IMongoDBQueryBuilder query,Class<T> clazz);
	public boolean remove(IMongoDBQueryBuilder query,IMongoDBData dbBean);
}
