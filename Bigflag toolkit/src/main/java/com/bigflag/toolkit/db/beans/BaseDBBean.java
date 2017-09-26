package com.bigflag.toolkit.db.beans;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.bigflag.toolkit.db.enums.DBDataStatus;
import com.bigflag.toolkit.db.interfaces.IDBService;
import com.bigflag.toolkit.exception.DBServiceNotInitException;
import com.bigflag.toolkit.ioc.ServiceFactory;
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
 *         Create at: 2017年9月19日 下午11:56:26
 */
public class BaseDBBean {
	private String uuid;
	private String createUserUUID;
	private String updateUserUUID;
	private String softDeleteUserUUID;
	private Date createTime;
	private Date updateTime;
	private Date softDeleteTime;
	private DBDataStatus dataStatus;
	
	public static String[] resverdAttributes=new String[]{
													"uuid",
													"createUserUUID",
													"updateUserUUID",
													"softDeleteUserUUID",
													"createTime",
													"updateTime",
													"softDeleteTime",
													"dataStatus"};
	
	private static IDBService dbService;
	
	static{
		dbService=ServiceFactory.getInstance().getDBService("");
	}
	
	private boolean isDBServiceInit()
	{
		return dbService!=null&&dbService.isInit();
	}
	
	public void createTable() throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		dbService.createTable(this);
	}
	
	public String save() throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		return dbService.saveAndReturnUUID(this);
	}
	
	public void update() throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		dbService.update(this);
	}
	
	public String saveOrUpdate() throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		return dbService.saveOrUpdate(this);
	}
	
	public void deleteSoft() throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		dbService.deleteSoft(this);
	}
	
	public void deleteHard() throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		dbService.deleteHard(this);
	}
	
	public boolean findAndLoad(String uuid) throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		return dbService.findAndLoad(this);
	}
	
	public boolean findWithAttributesAndLoad(String ... attributes) throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		return dbService.findWithAttributesAndLoad(attributes);
	}
	
	public List<BaseDBBean> findDBBeansWithAttributes(String ... attributes) throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		return dbService.findDBBeansWithAttributes(attributes);
	}
	
	public boolean startTransaction() throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		return dbService.startTransaction(this);
	}
	
	public boolean commitTransaction() throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		return dbService.commitTransaction(this);
	}
	
	public boolean rollbackTransaction() throws DBServiceNotInitException
	{
		if(!isDBServiceInit())
		{
			throw new DBServiceNotInitException();
		}
		return dbService.rollbackTransaction(this);
	}

	public static BaseDBBean newInstance(){
		return new BaseDBBean();
	}

	public void setUuid(String uuid){
		this.uuid=uuid;
	}

	public String getUuid(){
		return this.uuid;
	}

	public BaseDBBean uuid(String uuid){
		this.uuid=uuid;
		return this;
	}

	public void setCreateUserUUID(String createUserUUID){
		this.createUserUUID=createUserUUID;
	}

	public String getCreateUserUUID(){
		return this.createUserUUID;
	}

	public BaseDBBean createUserUUID(String createUserUUID){
		this.createUserUUID=createUserUUID;
		return this;
	}

	public void setUpdateUserUUID(String updateUserUUID){
		this.updateUserUUID=updateUserUUID;
	}

	public String getUpdateUserUUID(){
		return this.updateUserUUID;
	}

	public BaseDBBean updateUserUUID(String updateUserUUID){
		this.updateUserUUID=updateUserUUID;
		return this;
	}

	public void setSoftDeleteUserUUID(String softDeleteUserUUID){
		this.softDeleteUserUUID=softDeleteUserUUID;
	}

	public String getSoftDeleteUserUUID(){
		return this.softDeleteUserUUID;
	}

	public BaseDBBean softDeleteUserUUID(String softDeleteUserUUID){
		this.softDeleteUserUUID=softDeleteUserUUID;
		return this;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public BaseDBBean createTime(Date createTime){
		this.createTime=createTime;
		return this;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}

	public Date getUpdateTime(){
		return this.updateTime;
	}

	public BaseDBBean updateTime(Date updateTime){
		this.updateTime=updateTime;
		return this;
	}

	public void setSoftDeleteTime(Date softDeleteTime){
		this.softDeleteTime=softDeleteTime;
	}

	public Date getSoftDeleteTime(){
		return this.softDeleteTime;
	}

	public BaseDBBean softDeleteTime(Date softDeleteTime){
		this.softDeleteTime=softDeleteTime;
		return this;
	}

	public void setDataStatus(DBDataStatus dataStatus){
		this.dataStatus=dataStatus;
	}

	public DBDataStatus getDataStatus(){
		return this.dataStatus;
	}

	public BaseDBBean dataStatus(DBDataStatus dataStatus){
		this.dataStatus=dataStatus;
		return this;
	}
}