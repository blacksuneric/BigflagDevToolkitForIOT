/**
 * 
 */
package com.bigflag.toolkit.tool.http.services;

import com.bigflag.toolkit.tool.http.interfaces.IHttpCompressor;
import com.bigflag.toolkit.tool.http.interfaces.IHttpDecompressor;
import com.bigflag.toolkit.tool.http.interfaces.IHttpDecrypter;
import com.bigflag.toolkit.tool.http.interfaces.IHttpEncrypter;

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
 *         Create at:2017年9月21日 下午4:21:28
 */
public class HttpToolHelperFactory {
	public static final IHttpEncrypter NOTHING_TO_DO_ENCRYPTER;
	public static final IHttpDecrypter NOTHING_TO_DO_DECRYPTER;
	public static final IHttpCompressor NOTHING_TO_DO_COMPRESSOR;
	public static final IHttpDecompressor NOTHING_TO_DO_DECOMPRESSOR;
	
	
	static{
		NOTHING_TO_DO_ENCRYPTER=new IHttpEncrypter() {
			
			public byte[] encrypt(byte[] data) {
				// TODO Auto-generated method stub
				return data;
			}
		};
		
		NOTHING_TO_DO_DECRYPTER=new IHttpDecrypter() {
			
			public byte[] decrypt(byte[] data) {
				// TODO Auto-generated method stub
				return data;
			}
		};
		
		NOTHING_TO_DO_COMPRESSOR=new IHttpCompressor() {
			
			public byte[] compress(byte[] data) {
				// TODO Auto-generated method stub
				return data;
			}
		};
		
		NOTHING_TO_DO_DECOMPRESSOR=new IHttpDecompressor() {
			
			public byte[] decompress(byte[] data) {
				// TODO Auto-generated method stub
				return data;
			}
		};
	}
}
