/**
 * 
 */
package com.bigflag.toolkit.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigflag.toolkit.tool.http.impl.DefaultHttpPostToolImpl;
import com.bigflag.toolkit.tool.http.services.HttpToolHelperFactory;

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
		DefaultHttpPostToolImpl impl = new DefaultHttpPostToolImpl();
		// Map<String,String> params=new HashMap<String,String>();
		// params.put("key1", "value1");
		// params.put("key2", "value2");
		// params.put("key3", "value3");
		// String result=impl.postParameter("http://127.0.0.1:8089", params);

		byte[] result = impl.postCompressedBytes("http://127.0.0.1:8089", new byte[] { 48, 49, 50 }, HttpToolHelperFactory.NOTHING_TO_DO_COMPRESSOR,
				HttpToolHelperFactory.NOTHING_TO_DO_DECOMPRESSOR);
		System.out.println(Arrays.toString(result));
		System.out.println(HttpToolHelperFactory.NOTHING_TO_DO_COMPRESSOR);
		System.out.println(HttpToolHelperFactory.NOTHING_TO_DO_DECOMPRESSOR);
		System.out.println(HttpToolHelperFactory.NOTHING_TO_DO_DECRYPTER);
		System.out.println(HttpToolHelperFactory.NOTHING_TO_DO_ENCRYPTER);
	}

}
