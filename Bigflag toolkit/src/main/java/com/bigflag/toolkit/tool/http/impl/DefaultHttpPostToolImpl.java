/**
 * 
 */
package com.bigflag.toolkit.tool.http.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.bigflag.toolkit.tool.http.interfaces.IHttpCompressor;
import com.bigflag.toolkit.tool.http.interfaces.IHttpDecompressor;
import com.bigflag.toolkit.tool.http.interfaces.IHttpDecrypter;
import com.bigflag.toolkit.tool.http.interfaces.IHttpEncrypter;
import com.bigflag.toolkit.tool.http.interfaces.IHttpToolService;

/***
 * 
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
 *         Create at:2017年9月21日 下午1:41:53
 */
public class DefaultHttpPostToolImpl implements IHttpToolService {

	private ThreadLocal<byte[]> postBytes = new ThreadLocal<byte[]>();
	private ThreadLocal<IHttpCompressor> compressorLocal = new ThreadLocal<IHttpCompressor>();
	private ThreadLocal<IHttpDecompressor> decompressorLocal = new ThreadLocal<IHttpDecompressor>();
	private ThreadLocal<IHttpEncrypter> encrypterLocal = new ThreadLocal<IHttpEncrypter>();
	private ThreadLocal<IHttpDecrypter> decrypterLocal = new ThreadLocal<IHttpDecrypter>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.http.interfaces.IHttpToolService#postParameter
	 * (java.lang.String, java.util.Map)
	 */
	public String postParameter(String url, Map<String, String> params) {
		String result = "";
		HttpPost httpRequst = new HttpPost(url);
		httpRequst.setConfig(RequestConfig.custom().setConnectTimeout(1000 * 10).setConnectionRequestTimeout(1000 * 10).setSocketTimeout(1000 * 10).build());
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			nameValuePair.add(new BasicNameValuePair(key, params.get(key)));
		}
		try {
			httpRequst.setEntity(new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8));
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse httpResponse = httpClient.execute(httpRequst);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public byte[] doPostBytes(String url, byte[] bytes) {
		byte[] result = new byte[0];
		HttpPost httpRequst = new HttpPost(url);
		httpRequst.setConfig(RequestConfig.custom().setConnectTimeout(1000 * 10).setConnectionRequestTimeout(1000 * 10).setSocketTimeout(1000 * 10).build());
		try {
			httpRequst.setEntity(EntityBuilder.create().setBinary(bytes).build());
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse httpResponse = httpClient.execute(httpRequst);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toByteArray(httpEntity);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigflag.toolkit.tool.http.interfaces.IHttpToolService#startToPostBytes
	 * (byte[])
	 */
	@Override
	public HttpToolDecorator startToPostBytes(byte[] bytes) {
		postBytes.set(bytes);
		return new HttpToolDecorator(this);
	}
	
	public class HttpToolDecorator implements IHttpToolDecorator
	{
		private IHttpToolService httpToolService;
		
		public HttpToolDecorator(IHttpToolService httpToolService) {
			this.httpToolService=httpToolService;
		}

		/* (non-Javadoc)
		 * @see com.bigflag.toolkit.tool.http.interfaces.IHttpToolService.IHttpToolDecorator#doPostBytes(java.lang.String)
		 */
		@Override
		public byte[] doPostBytes(String url) {
			if (postBytes.get() != null) {
				byte[] data = postBytes.get();
				if (encrypterLocal.get() != null) {
					data = encrypterLocal.get().encrypt(postBytes.get());
				}
				if (compressorLocal.get() != null) {
					data = compressorLocal.get().compress(data);
				}
				byte[] result = this.httpToolService.doPostBytes(url, data);
				if (decrypterLocal.get() != null) {
					result = decrypterLocal.get().decrypt(result);
				}
				if (decompressorLocal.get() != null) {
					result = decompressorLocal.get().decompress(result);
				}
				postBytes.remove();
				encrypterLocal.remove();
				decrypterLocal.remove();
				compressorLocal.remove();
				decompressorLocal.remove();
				return this.httpToolService.doPostBytes(url, postBytes.get());
			} else {
				return new byte[] {};
			}
		}

		/* (non-Javadoc)
		 * @see com.bigflag.toolkit.tool.http.interfaces.IHttpToolService.IHttpToolDecorator#compressBytes(com.bigflag.toolkit.tool.http.interfaces.IHttpCompressor, com.bigflag.toolkit.tool.http.interfaces.IHttpDecompressor)
		 */
		@Override
		public IHttpToolDecorator compressBytes(IHttpCompressor compressor, IHttpDecompressor httpDecompressor) {
			compressorLocal.set(compressor);
			decompressorLocal.set(httpDecompressor);
			return this;
		}

		/* (non-Javadoc)
		 * @see com.bigflag.toolkit.tool.http.interfaces.IHttpToolService.IHttpToolDecorator#encryptBytes(com.bigflag.toolkit.tool.http.interfaces.IHttpEncrypter, com.bigflag.toolkit.tool.http.interfaces.IHttpDecrypter)
		 */
		@Override
		public IHttpToolDecorator encryptBytes(IHttpEncrypter httpEncrypter, IHttpDecrypter httpDecrypter) {
			encrypterLocal.set(httpEncrypter);
			decrypterLocal.set(httpDecrypter);
			return this;
		}
		
	}


}
