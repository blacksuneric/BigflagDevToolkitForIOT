package com.bigflag.toolkit.tool.http.enums;
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
 *         Create at:2017年9月20日 下午1:26:23
 */
public enum PostByteFeature {
	NORMAL,
	COMPRESSED,
	ENCRYPTED,
	;
	public final int mask;
	PostByteFeature(){
		mask = (1 << ordinal());
	}

	public final int getMask() {
		 return mask;
	}
	public static boolean isEnabled(int features, PostByteFeature feature) {
		 return (features & feature.mask) != 0;
	}
	public static int config(int features, PostByteFeature feature, boolean state) {
		if (state) {
			features |= feature.mask;
		} else {
			features &= ~feature.mask;		}
		return features;
	}
	public static int of(PostByteFeature[] features) {
		if (features == null) {
			return 0;
		}

		int value = 0;

		for (PostByteFeature feature: features) {
			value |= feature.mask;
		}

		return value;
	}

}