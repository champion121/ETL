package com.tl.job007.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	public static String getRegexValue(String input, String regex,
			int groupIndex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			return matcher.group(groupIndex);
		}
		return null;
	}
}
