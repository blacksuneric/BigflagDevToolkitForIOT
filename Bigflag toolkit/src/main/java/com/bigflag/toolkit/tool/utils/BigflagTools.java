/**
 * 
 */
package com.bigflag.toolkit.tool.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.joda.time.DateTime;

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
 *         Create at:2017年9月27日 下午4:08:00
 */
public class BigflagTools {
	public static String byteToHexString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
			sb.append(",");
		}
		return sb.substring(0, sb.length() - 1);
	}

	public static byte[] hexStringToBytes(String hexStrings) {
		String[] hexs = hexStrings.split(",");
		byte[] bs = new byte[hexs.length];
		for (int i = 0; i < bs.length; i++) {
			if (hexs[i].length() == 1) {
				hexs[i] = "0" + hexs[i];
			}
			bs[i] = uniteBytes(hexs[i].substring(0, 1), hexs[i].substring(1, 2));
		}

		return bs;
	}

	public static String getXorString(String hexStr) {
		if (hexStr.contains(",")) {
			hexStr = hexStr.replace(",", "");
		}
		int xor = Integer.parseUnsignedInt(hexStr.substring(0, 2), 16);
		for (int i = 2; i < hexStr.length() - 1; i = i + 2) {
			String h = hexStr.substring(i, i + 2);
			int hexDec = Integer.parseUnsignedInt(h, 16);
			xor = xor ^ hexDec;
		}
		String xorr = Integer.toString(xor, 16).toUpperCase();
		if (xorr.length() == 1) {
			xorr = "0" + xorr;
		}
		return xorr;

	}

	public static String getCurrentTimeLongStr() {
		return DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
	}

	public static byte uniteBytes(String src0, String src1) {

		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	public static byte[] objectToByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			bytes = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return bytes;
	}
	
	public static <T> T byteArrayToObject(byte[] bytes,Class<T> clazz)
	{
		ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
		ObjectInputStream objectInputStream=null;
		try {
			objectInputStream=new ObjectInputStream(byteArrayInputStream);
			return (T)objectInputStream.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (byteArrayInputStream != null) {
				try {
					byteArrayInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return null;
	}
}
