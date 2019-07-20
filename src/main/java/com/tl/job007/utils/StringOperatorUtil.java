package com.tl.job007.utils;

/**
 * 字符串操作工具类
 * 
 * @author tianliang
 *
 * @date 2019年6月5日
 */
public class StringOperatorUtil {
	public static boolean isBlank(String input) {
		if (input == null || input.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotBlank(String input) {
		return !isBlank(input);
	}
}
