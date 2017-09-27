/**
 * 
 */
package com.bigflag.toolkit.tool.utils;

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
			if(hexs[i].length()==1)
			{
				hexs[i]="0"+hexs[i];
			}
			bs[i] = uniteBytes(hexs[i].substring(0, 1), hexs[i].substring(1, 2));
		}

		return bs;
	}
	
	public static byte uniteBytes(String src0, String src1) {

		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}
}
