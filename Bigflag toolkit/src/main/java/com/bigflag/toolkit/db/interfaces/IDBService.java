/**
 * 
 */
package com.bigflag.toolkit.db.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import com.bigflag.toolkit.db.beans.BaseDBBean;
import com.bigflag.toolkit.db.beans.BaseDBConfigBean;

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
 *         Create at:下午10:24:33 
 */
public interface IDBService {

	/**
	 * @param baseDBBean
	 */
	String saveAndReturnUUID(BaseDBBean baseDBBean);

	/**
	 * @param baseDBBean
	 */
	boolean update(BaseDBBean baseDBBean);


	/**
	 * @param baseDBBean
	 */
	boolean deleteSoft(BaseDBBean baseDBBean);

	/**
	 * @param baseDBBean
	 */
	boolean deleteHard(BaseDBBean baseDBBean);

	/**
	 * @param baseDBBean
	 * @param uuid
	 * @return
	 */
	boolean findAndLoad(BaseDBBean baseDBBean);

	/**
	 * @param attributes
	 * @return
	 */
	boolean findWithAttributesAndLoad(BaseDBBean baseDBBean,String[] attributes);

	/**
	 * @param baseDBBean
	 * @return 
	 */
	boolean startTransaction(BaseDBBean baseDBBean);

	/**
	 * @param baseDBBean
	 * @return 
	 */
	boolean commitTransaction(BaseDBBean baseDBBean);
	
	boolean rollbackTransaction(BaseDBBean baseDBBean);

	/**
	 * @param baseDBBean
	 */
	void createTable(BaseDBBean baseDBBean);

	/**
	 * @return
	 */
	boolean isInit();
	
	/***
	 * 
	 * @param dbConfigBean
	 */
	boolean initConnection(BaseDBConfigBean dbConfigBean);
	
	Connection getDBConnection() throws SQLException;
	
	Connection getDBConnection(BaseDBBean baseDBBean) throws SQLException;
	
	void enableSQLRouteDispatchService(IDBRouterDispatchService dbRouteService);

}
