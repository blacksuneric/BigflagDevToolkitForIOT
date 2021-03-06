/**
 * 
 */
package com.bigflag.toolkit.rpc.interfaces;

import java.util.List;

import com.bigflag.toolkit.rpc.beans.BaseRPCConfig;
import com.bigflag.toolkit.rpc.beans.RemoteInterfaceInfoProtobuf;
import com.bigflag.toolkit.rpc.beans.RemoteInterfaceInfoProtobuf.Message;

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
 *         Create at:2017年10月14日 下午7:04:39
 */
public interface IRemoteCallService {
	public boolean connectToESB(BaseRPCConfig esbConfig);
	public <T> T buildStub(Class<T> clazz);
	public boolean regiesterServiceToESB(RemoteInterfaceInfoProtobuf.Message interfaceInfo);
	public List<Message> queryRemoteInterfaceInfo(String fullInterfaceName, int version, List<String> tags);
}
