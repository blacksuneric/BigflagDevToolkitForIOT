/**
 * 
 */
package com.bigflag.toolkit.tool.http.interfaces;

import java.util.Map;

import com.bigflag.toolkit.tool.http.enums.PostByteFeature;

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
 *         Create at:下午11:05:48 
 */
public interface IHttpToolService {
	/***
	 * simple post to url 
	 * @param url the target url to receive post
	 * @param params the parameters for posting
	 */
	public String postParameter(String url,Map<String,String> params);
	/***
	 * post bytes to url
	 * @param url the taget url to receive post
	 * @param bytes the bytes to post
	 * @param postByteFeature {@link PostByteFeature} the feature for posting bytes
	 */
	public byte[] postBytes(String url,byte[] bytes);
	
	public byte[] postCompressedBytes(String url,byte[] bytes,IHttpCompressor compressor,IHttpDecompressor httpDecompressor);
	
	public byte[] postEncryptedBytes(String url,byte[] bytes,IHttpEncrypter httpEncrypter, IHttpDecrypter httpDecrypter);
	
	public byte[] postCompressedEncryptedBytes(String url,byte[] bytes,IHttpCompressor compressor,IHttpDecompressor httpDecompressor,IHttpEncrypter httpEncrypter, IHttpDecrypter httpDecrypter);
	
}
