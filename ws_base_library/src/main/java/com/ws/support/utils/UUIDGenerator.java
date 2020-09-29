package com.ws.support.utils;

import java.util.UUID;

/**
 * <p>Title:UUIDGenerator.java</p>
 * <p>Description:UUID生成工具类</p>
 * @author Johnny.xu
 * @date 2016年11月21日
 */
public class UUIDGenerator {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String[] getUUID(int number) {
		if (number < 1) {
			return new String[0];
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
	
}
