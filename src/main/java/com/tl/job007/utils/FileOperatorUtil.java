package com.tl.job007.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileOperatorUtil {
	/**
	 * 给定filepath和charset，返回其文件对应内容的list集合形式
	 * 
	 * @param filePath
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFileToList(String filePath, String charset)
			throws IOException {
		// 定义输入路径，及返回的结果集合
		List<String> lineList = new ArrayList<String>();

		// 做文件IO操作
		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, charset);
		BufferedReader br = new BufferedReader(isr);
		String tempLine = null;
		while ((tempLine = br.readLine()) != null) {
			lineList.add(tempLine);
		}
		br.close();
		return lineList;
	}

	/**
	 * 给定任意文件夹路径字符串，获取其下所有的子文件夹和子文件。 其中filterString可以过滤提取出满足该条件的子目录和子文件的集合
	 * 
	 * @param dirPath
	 * @param isRoot
	 * @param filterString
	 * @return
	 */
	public static List<String> getAllSubFiles(String dirPath, boolean isRoot,
			String filterString) {
		if (StringOperatorUtil.isBlank(dirPath)) {
			return null;
		}
		// 将字符串形式的文件路径对象化
		List<String> allSubFileList = new ArrayList<String>();
		File fileObj = new File(dirPath);
		// 判断该文件是否存在，若不存在则直接返回null
		if (!fileObj.exists()) {
			return null;
		}
		// 如果是目录，则递归下去,如果是文件则加入集合即可
		if (fileObj.isDirectory()) {
			File[] fileArray = fileObj.listFiles();
			for (File subFile : fileArray) {
				List<String> tempList = getAllSubFiles(subFile.toString(),
						false, filterString);
				allSubFileList.addAll(tempList);
			}
			if (!isRoot) {
				if (filterString == null
						|| filterString != null
						&& fileObj.getName().split("\\.")[0]
								.matches(filterString)) {
					allSubFileList.add(fileObj.toString());
				}
			}
		} else {
			if (filterString == null
					|| (filterString != null && fileObj.getName().split("\\.")[0]
							.matches(filterString))) {
				allSubFileList.add(fileObj.toString());
			}
		}
		return allSubFileList;
	}

	public static List<String> getFirstLevelSubDir(String dirPath) {
		if (StringOperatorUtil.isBlank(dirPath)) {
			return null;
		}
		// 将字符串形式的文件路径对象化
		List<String> firstLevelSubFileList = new ArrayList<String>();
		File fileObj = new File(dirPath);
		// 判断该文件是否存在，若不存在则直接返回null
		if (!fileObj.exists()) {
			return null;
		}
		// 如果是目录，则递归下去,如果是文件则加入集合即可
		if (fileObj.isDirectory()) {
			File[] fileArray = fileObj.listFiles();
			for (File subFile : fileArray) {
				if (subFile.isDirectory()) {
					firstLevelSubFileList.add(subFile.getName());
				}
			}
		}
		return firstLevelSubFileList;
	}

	public static void writeStrToFile(String destFilePath, String content,
			String charset) throws UnsupportedEncodingException, IOException {
		FileOutputStream fos = new FileOutputStream(destFilePath,true);
		fos.write(content.getBytes(charset));
		fos.close();
	}

}
