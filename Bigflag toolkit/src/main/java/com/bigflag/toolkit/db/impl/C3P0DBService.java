/**
 * 
 */
package com.bigflag.toolkit.db.impl;

import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.bigflag.toolkit.db.beans.BaseDBConfigBean;
import com.bigflag.toolkit.db.beans.BaseDBBean;
import com.bigflag.toolkit.db.enums.DBDataStatus;
import com.bigflag.toolkit.db.interfaces.IDBService;
import com.mchange.v2.c3p0.ComboPooledDataSource;

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
 *         Create at:2017年9月24日 上午11:28:24
 */
public class C3P0DBService implements IDBService {

	private static DataSource ds;
	private Map<BaseDBBean, Connection> transactionDBMap = new HashMap<BaseDBBean, Connection>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#saveAndReturnUUID(com.bigflag
	 * .toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public String saveAndReturnUUID(BaseDBBean baseDBBean) {
		String uuid=UUID.randomUUID().toString();
		baseDBBean.setUuid(uuid);
		this.verifyBaseDBBean(baseDBBean);
		try {
			Connection conn = this.getDBConnection(baseDBBean);
			String tableName = this.getClassName(baseDBBean);
			StringBuilder sbSQL = new StringBuilder();
			Map<String, Object> fileds = PropertyUtils.describe(baseDBBean);
			sbSQL.append("insert into ").append(tableName).append(" (");
			for (String key : fileds.keySet()) {
				if (ArrayUtils.contains(BaseDBBean.resverdAttributes, key) || key.equalsIgnoreCase("class")) {
					continue;
				}
				sbSQL.append(key).append(",");
			}
			sbSQL.append(BaseDBBean.resverdAttributes[0]).append(",");
			sbSQL.append(BaseDBBean.resverdAttributes[1]).append(",");
			sbSQL.append(BaseDBBean.resverdAttributes[2]).append(",");
			sbSQL.append(BaseDBBean.resverdAttributes[3]).append(",");
			sbSQL.append(BaseDBBean.resverdAttributes[4]).append(",");
			sbSQL.append(BaseDBBean.resverdAttributes[5]).append(",");
			sbSQL.append(BaseDBBean.resverdAttributes[6]).append(",");
			sbSQL.append(BaseDBBean.resverdAttributes[7]);

			sbSQL.append(") values(").append(StringUtils.repeat("?", ",", fileds.keySet().size()-1)).append(")");
			PreparedStatement stat = conn.prepareStatement(sbSQL.toString());
			int index = 1;
			for (String key : fileds.keySet()) {
				if (ArrayUtils.contains(BaseDBBean.resverdAttributes, key) || key.equalsIgnoreCase("class")) {
					continue;
				}
				stat.setObject(index++, fileds.get(key));
			}
			stat.setObject(index++, uuid);
			stat.setObject(index++, "createUserUUID");
			stat.setObject(index++, "updateUserUUID");
			stat.setObject(index++, "softDeleteUserUUID");
			stat.setObject(index++, DateTime.now().toDate());
			stat.setObject(index++, DateTime.now().toDate());
			stat.setObject(index++, DateTime.now().toDate());
			stat.setObject(index++, DBDataStatus.NORMAL.name());
			stat.execute();
			return uuid;
		} catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#update(com.bigflag.toolkit
	 * .db.beans.BaseDBBean)
	 */
	@Override
	public boolean update(BaseDBBean baseDBBean) {
		this.verifyBaseDBBean(baseDBBean);
		try {
			Connection conn = this.getDBConnection(baseDBBean);
			String tableName = this.getClassName(baseDBBean);
			StringBuilder sbSQL = new StringBuilder();
			Map<String, Object> fileds = PropertyUtils.describe(baseDBBean);
			sbSQL.append("update ").append(tableName).append(" set ");
			for (String key : fileds.keySet()) {
				if (ArrayUtils.contains(BaseDBBean.resverdAttributes, key) || key.equalsIgnoreCase("class")) {
					continue;
				}
				sbSQL.append(key).append("=?").append(",");
			}
			sbSQL.append(BaseDBBean.resverdAttributes[0]).append("=?,");
			sbSQL.append(BaseDBBean.resverdAttributes[1]).append("=?,");
			sbSQL.append(BaseDBBean.resverdAttributes[2]).append("=?,");
			sbSQL.append(BaseDBBean.resverdAttributes[3]).append("=?,");
			sbSQL.append(BaseDBBean.resverdAttributes[4]).append("=?,");
			sbSQL.append(BaseDBBean.resverdAttributes[5]).append("=?,");
			sbSQL.append(BaseDBBean.resverdAttributes[6]).append("=?,");
			sbSQL.append(BaseDBBean.resverdAttributes[7]).append("=? ");

			sbSQL.append(" where uuid=\"").append(baseDBBean.getUuid()).append("\"");
			PreparedStatement stat = conn.prepareStatement(sbSQL.toString());
			int index = 1;
			for (String key : fileds.keySet()) {
				if (ArrayUtils.contains(BaseDBBean.resverdAttributes, key) || key.equalsIgnoreCase("class")) {
					continue;
				}
				stat.setObject(index++, fileds.get(key));
			}
			stat.setObject(index++, baseDBBean.getUuid());
			stat.setObject(index++, baseDBBean.getCreateUserUUID());
			stat.setObject(index++, baseDBBean.getUpdateUserUUID());
			stat.setObject(index++, baseDBBean.getSoftDeleteUserUUID());
			stat.setObject(index++, baseDBBean.getCreateTime());
			stat.setObject(index++, DateTime.now().toDate());
			stat.setObject(index++, baseDBBean.getSoftDeleteTime());
			stat.setObject(index++, baseDBBean.getDataStatus().name());
			return stat.execute();
		} catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#saveOrUpdate(com.bigflag
	 * .toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public String saveOrUpdate(BaseDBBean baseDBBean) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#deleteSoft(com.bigflag.toolkit
	 * .db.beans.BaseDBBean)
	 */
	@Override
	public void deleteSoft(BaseDBBean baseDBBean) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#deleteHard(com.bigflag.toolkit
	 * .db.beans.BaseDBBean)
	 */
	@Override
	public void deleteHard(BaseDBBean baseDBBean) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#findAndLoad(com.bigflag.
	 * toolkit.db.beans.BaseDBBean, java.lang.String)
	 */
	@Override
	public boolean findAndLoad(BaseDBBean baseDBBean) {
		this.verifyBaseDBBean(baseDBBean);
		
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#findWithAttributesAndLoad
	 * (java.lang.String[])
	 */
	@Override
	public boolean findWithAttributesAndLoad(String[] attributes) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#findDBBeansWithAttributes
	 * (java.lang.String[])
	 */
	@Override
	public List<BaseDBBean> findDBBeansWithAttributes(String[] attributes) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#startTransaction(com.bigflag
	 * .toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public boolean startTransaction(BaseDBBean baseDBBean) {
		try {
			Connection conn = this.getDBConnection();
			conn.setAutoCommit(false);
			transactionDBMap.put(baseDBBean, conn);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#commitTransaction(com.bigflag
	 * .toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public boolean commitTransaction(BaseDBBean baseDBBean) {
		Connection conn = transactionDBMap.get(baseDBBean);
		try {
			if (conn == null || !conn.isValid(10)) {
				return false;
			} else {
				conn.commit();
				transactionDBMap.remove(baseDBBean);
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#rollbackTransaction(com.
	 * bigflag.toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public boolean rollbackTransaction(BaseDBBean baseDBBean) {
		Connection conn = transactionDBMap.get(baseDBBean);
		try {
			if (conn == null || !conn.isValid(10)) {
				return false;
			} else {
				conn.rollback();
				transactionDBMap.remove(baseDBBean);
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#createTable(com.bigflag.
	 * toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public void createTable(BaseDBBean baseDBBean) {
		try {
			Connection conn = this.getDBConnection(baseDBBean);
			String tableName = this.getClassName(baseDBBean);
			StringBuilder sbSQL = new StringBuilder();
			StringBuilder sbDropTable = new StringBuilder();
			sbDropTable.append("DROP TABLE IF EXISTS ").append(tableName).append(";");
			sbSQL.append("CREATE TABLE ").append(tableName).append(" (").append(" id int(10) unsigned NOT NULL AUTO_INCREMENT,");
			PropertyDescriptor[] descriptor = PropertyUtils.getPropertyDescriptors(baseDBBean);
			for (PropertyDescriptor desc : descriptor) {
				if (desc.getPropertyType() == String.class) {
					sbSQL.append(desc.getName()).append(" varchar(255) COLLATE utf8_bin DEFAULT NULL,");
				} else if (desc.getPropertyType() == Integer.class) {
					sbSQL.append(desc.getName()).append(" int(10) DEFAULT NULL,");
				} else if (desc.getPropertyType() == Boolean.class) {
					sbSQL.append(desc.getName()).append(" tinyint(1) DEFAULT NULL,");
				} else if (desc.getPropertyType() == Date.class) {
					sbSQL.append(desc.getName()).append(" datetime DEFAULT NULL,");
				} else if(desc.getPropertyType()==DBDataStatus.class)
				{
					sbSQL.append(desc.getName()).append(" varchar(255) COLLATE utf8_bin DEFAULT NULL,");
				}
			}
			sbSQL.append("PRIMARY KEY (`id`))").append(" ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;");

			Statement stat = conn.createStatement();
			stat.execute(sbDropTable.toString());
			stat.execute(sbSQL.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bigflag.toolkit.db.interfaces.IDBService#isInit()
	 */
	@Override
	public boolean isInit() {
		// TODO Auto-generated method stub
		return ds != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#initConnection(com.bigflag
	 * .toolkit.db.beans.BaseBDConfigBean)
	 */
	@Override
	public boolean initConnection(BaseDBConfigBean dbConfigBean) {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(dbConfigBean.getDriver());
			cpds.setJdbcUrl(dbConfigBean.getJdbcURL());
			cpds.setUser(dbConfigBean.getUserName());
			cpds.setPassword(dbConfigBean.getPwd());
			cpds.setMinPoolSize(10);
			cpds.setMaxPoolSize(20);
			cpds.setInitialPoolSize(10);
			cpds.setAcquireRetryDelay(3000);
			cpds.setMaxIdleTime(60);
			cpds.setIdleConnectionTestPeriod(60);
			ds = cpds;
			return true;
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			ds = null;
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bigflag.toolkit.db.interfaces.IDBService#getDBConnection()
	 */
	@Override
	public Connection getDBConnection() throws SQLException {
		return ds.getConnection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.db.interfaces.IDBService#getDBConnection(com.bigflag
	 * .toolkit.db.beans.BaseDBBean)
	 */
	@Override
	public Connection getDBConnection(BaseDBBean baseDBBean) throws SQLException {
		Connection conn = transactionDBMap.get(baseDBBean);
		if (conn == null) {
			conn = this.getDBConnection();
		}
		return conn;
	}

	private String getClassName(BaseDBBean baseDBBean) {
		if (baseDBBean == null) {
			throw new NullPointerException("DBBean is null");
		}
		return baseDBBean.getClass().getSimpleName();
	}
	
	private void verifyBaseDBBean(BaseDBBean baseDBBean)
	{
		if(baseDBBean==null)
		{
			throw new NullPointerException("baseDBBean is null");
		}else if(!StringUtils.isNotBlank(baseDBBean.getUuid()))
		{
			throw new IllegalArgumentException("the db bean uuid cannot be empty");
		}
	}

}
