/**
 * 
 */
package com.bigflag.toolkit.tool.coordinator.impl;

import java.util.List;

import com.bigflag.toolkit.tool.coordinator.beans.BaseCoordinatorConfigBean;
import com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService;

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
 *         Create at: 2017年10月17日 下午10:51:59 
 */
public class DefaultZooKeeperCoordinatorService implements ICoordinatorToolService {

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService#isInit()
	 */
	@Override
	public boolean isInit() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService#connectServer(com.bigflag.toolkit.tool.coordinator.beans.BaseCoordinatorConfigBean)
	 */
	@Override
	public boolean connectServer(BaseCoordinatorConfigBean configBean) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService#getNodeData(java.lang.String, boolean, com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService.OnDataWatchNodeChanged)
	 */
	@Override
	public byte[] getNodeData(String nodePath, boolean repeatedWatchChange, OnDataWatchNodeChanged onDataWatchNodeChanged) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService#getNodeChildren(java.lang.String, boolean, com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService.OnDataWatchNodeChanged)
	 */
	@Override
	public List<String> getNodeChildren(String nodePath, boolean repeatedWatchChange, OnDataWatchNodeChanged onDataWatchNodeChanged) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService#existNode(java.lang.String, boolean, com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService.OnDataWatchNodeChanged)
	 */
	@Override
	public boolean existNode(String nodePath, boolean repeatedWatchChange, OnDataWatchNodeChanged onDataWatchNodeChanged) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService#removePath(java.lang.String)
	 */
	@Override
	public boolean removePath(String nodePath) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService#createPersistentPath(java.lang.String, byte[], boolean)
	 */
	@Override
	public boolean createPersistentPath(String nodePath, byte[] data, boolean isSequential) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService#createEphemeralPath(java.lang.String, byte[], boolean)
	 */
	@Override
	public boolean createEphemeralPath(String nodePath, byte[] data, boolean isSequential) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bigflag.toolkit.tool.coordinator.interfaces.ICoordinatorToolService#setData(java.lang.String, byte[])
	 */
	@Override
	public boolean setData(String nodePath, byte[] data) {
		// TODO Auto-generated method stub
		return false;
	}

}
