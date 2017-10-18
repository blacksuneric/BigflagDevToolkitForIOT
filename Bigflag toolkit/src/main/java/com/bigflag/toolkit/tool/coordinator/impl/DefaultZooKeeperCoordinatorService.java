/**
 * 
 */
package com.bigflag.toolkit.tool.coordinator.impl;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.bigflag.toolkit.exception.CoordinatorServiceNotInitException;
import com.bigflag.toolkit.tool.coordinator.beans.BaseCoordinatorConfigBean;
import com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService;

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
 *         Create at: 2017年10月17日 下午10:51:59
 */
public class DefaultZooKeeperCoordinatorService implements ICoordinatorToolService, Watcher {
	private ZooKeeper zk;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService
	 * #isInit()
	 */
	@Override
	public boolean isInit() {
		// TODO Auto-generated method stub
		return zk != null;
	}
	
	private void checkInit()
	{
		if(!isInit())
		{
			throw new CoordinatorServiceNotInitException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService
	 * #connectServer
	 * (com.bigflag.toolkit.tool.coordinator.beans.BaseCoordinatorConfigBean)
	 */
	@Override
	public boolean connectServer(BaseCoordinatorConfigBean configBean) {
		try {
			zk = new ZooKeeper(configBean.getConnectUrl(), configBean.getTimeout(), this);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService
	 * #getNodeData(java.lang.String, boolean,
	 * com.bigflag.toolkit.tool.coordinator
	 * .interfaces.ICoordinatorToolService.OnDataWatchNodeChanged)
	 */
	@Override
	public byte[] getNodeData(String nodePath, boolean repeatedWatchChange, OnDataWatchNodeChanged onDataWatchNodeChanged) {
		Stat stat;
		try {
			if (!repeatedWatchChange) {
				stat = zk.exists(nodePath, false);
				if (stat != null) {
					byte[] data = zk.getData(nodePath, false, null);
					return data;
				} else {
					return new byte[] {};
				}
			} else {
				stat = zk.exists(nodePath, false);
				if (stat != null) {
					byte[] data = zk.getData(nodePath, new Watcher() {
						@Override
						public void process(WatchedEvent event) {
							if(onDataWatchNodeChanged!=null)
							{
								onDataWatchNodeChanged.processNodeChange(event.getType().getIntValue(), event.getPath(),null);
							}
						}
					}, null);
					return data;
				} else {
					return new byte[] {};
				}
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new byte[] {};
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService
	 * #getNodeChildren(java.lang.String, boolean,
	 * com.bigflag.toolkit.tool.coordinator
	 * .interfaces.ICoordinatorToolService.OnDataWatchNodeChanged)
	 */
	@Override
	public List<String> getNodeChildren(String nodePath, boolean repeatedWatchChange, OnDataWatchNodeChanged onDataWatchNodeChanged) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService
	 * #existNode(java.lang.String, boolean,
	 * com.bigflag.toolkit.tool.coordinator
	 * .interfaces.ICoordinatorToolService.OnDataWatchNodeChanged)
	 */
	@Override
	public boolean existNode(String nodePath, boolean repeatedWatchChange, OnDataWatchNodeChanged onDataWatchNodeChanged) {
		this.checkInit();
		try {
			if(!repeatedWatchChange)
			{
				return zk.exists(nodePath, false)==null;
			}else
			{
				return zk.exists(nodePath,new ExistWatcher(onDataWatchNodeChanged))==null;
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private class ExistWatcher implements Watcher
	{
		private OnDataWatchNodeChanged onDataWatchNodeChanged;
		/* (non-Javadoc)
		 * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
		 */
		@Override
		public void process(WatchedEvent event) {
			onDataWatchNodeChanged.processNodeChange(event.getType().getIntValue(), event.getPath(),null);
			try {
				zk.exists(event.getPath(), this);
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * @param onDataWatchNodeChanged
		 */
		public ExistWatcher(OnDataWatchNodeChanged onDataWatchNodeChanged) {
			super();
			this.onDataWatchNodeChanged = onDataWatchNodeChanged;
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService
	 * #removePath(java.lang.String)
	 */
	@Override
	public boolean removePath(String nodePath) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService
	 * #createPersistentPath(java.lang.String, byte[], boolean)
	 */
	@Override
	public boolean createPersistentPath(String nodePath, byte[] data, boolean isSequential) {
		try {
			if (isSequential) {
				zk.create(nodePath, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
			} else {
				zk.create(nodePath, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (KeeperException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService
	 * #createEphemeralPath(java.lang.String, byte[], boolean)
	 */
	@Override
	public boolean createEphemeralPath(String nodePath, byte[] data, boolean isSequential) {
		try {
			if (isSequential) {
				zk.create(nodePath, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			} else {
				zk.create(nodePath, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			}
		} catch (KeeperException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService
	 * #setData(java.lang.String, byte[])
	 */
	@Override
	public boolean setData(String nodePath, byte[] data) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void process(WatchedEvent arg0) {
		// TODO Auto-generated method stub

	}

}
