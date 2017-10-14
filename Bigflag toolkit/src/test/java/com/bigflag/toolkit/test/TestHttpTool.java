/**
 * 
 */
package com.bigflag.toolkit.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigflag.toolkit.rpc.impl.DefaultRemoteCallService;

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
 *         Create at:下午9:35:20
 */
public class TestHttpTool {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
//		DefaultHttpPostToolImpl impl = new DefaultHttpPostToolImpl();
		// Map<String,String> params=new HashMap<String,String>();
		// params.put("key1", "value1");
		// params.put("key2", "value2");
		// params.put("key3", "value3");
		// String result=impl.postParameter("http://127.0.0.1:8089", params);

//		byte[] result = impl.postCompressedBytes("http://127.0.0.1:8089", new byte[] { 48, 49, 50 }, HttpToolHelperFactory.NOTHING_TO_DO_COMPRESSOR,
//				HttpToolHelperFactory.NOTHING_TO_DO_DECOMPRESSOR);
//		byte[] result=impl.startToPostBytes(new byte[]{11,22,33}).compressBytes(null, null).encryptBytes(null, null).doPostBytes("http");
//		System.out.println(Arrays.toString(result));
//		System.out.println(HttpToolHelperFactory.NOTHING_TO_DO_COMPRESSOR);
//		System.out.println(HttpToolHelperFactory.NOTHING_TO_DO_DECOMPRESSOR);
//		System.out.println(HttpToolHelperFactory.NOTHING_TO_DO_DECRYPTER);
//		System.out.println(HttpToolHelperFactory.NOTHING_TO_DO_ENCRYPTER);
//		String code=ServiceFactory.getInstance().getDefaultEncryptedCodeDeviceService().getEncryptedCodeForDevice(DateTime.now().toDate(), "1234567");
//		System.out.println(code);
		
//		Class[] cs = {ITestRemote.class};
//		
//		Object obj =Proxy.newProxyInstance(ITestRemote.class.getClassLoader(), 
//				cs, new InvocationHandler() {
//			@Override
//			public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
//				System.out.println(arg1.getName()+" hello");
//				if(arg1.getReturnType().getTypeName().equals("int"))
//				{
//					return 1;
//				}else if(arg1.getReturnType().getTypeName().equals(String.class.getTypeName()))
//				{
//					return "asdf";
//				}
//				return null;
//			}
//		});
		
		DefaultRemoteCallService callService=new DefaultRemoteCallService();
		ITestRemote test=callService.buildStub(ITestRemote.class);
		
		System.out.println(test.add(1, 2));
		System.out.println(test.minus(2, 3));
		System.out.println(test.add(1, 2));
		System.out.println(test.minus(2, 3));
//		List<Class<?>> classes=ClassUtils.getAllInterfaces(obj.getClass());
//		for(Class<?> clazz:classes)
//		{
//			System.out.println(clazz);
//		}
//		System.out.println(obj.getClass());
		
	}

}
