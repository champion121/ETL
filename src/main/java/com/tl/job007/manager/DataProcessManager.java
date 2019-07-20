package com.tl.job007.manager;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;

import com.tl.job007.pojos.ContentInfoPojo;
import com.tl.job007.pojos.UserAndContentJoinPojo;
import com.tl.job007.pojos.UserInfoPojo;
import com.tl.job007.utils.Dom4JUtil;
import com.tl.job007.utils.FileOperatorUtil;
import com.tl.job007.utils.RegexUtil;
import com.tl.job007.utils.StaticValue;

public class DataProcessManager {
	public static String regex = "<content>([\\s\\S]*?)</content>";
	static Map<String, List<String>> dataTypeAndFilePathRelationMap = new HashMap<String, List<String>>();
	static {
		dataTypeAndFilePathRelationMap.put("user", null);
		dataTypeAndFilePathRelationMap.put("content", null);
	}

	public static UserAndContentJoinPojo getConstructObjList(
			Map<String, List<String>> typeAndFileListMap) throws IOException {
		// 遍历对应关系，校验准确性
		Set<String> keySet = dataTypeAndFilePathRelationMap.keySet();
		List<UserInfoPojo> userPojoList = new ArrayList<UserInfoPojo>();
		List<ContentInfoPojo> contentInfoPojoList = new ArrayList<ContentInfoPojo>();
		int lineXmlCounter = 0;
		int lineXmlCounterRight = 0;
		int lineXmlCounterError = 0;
		for (String key : keySet) {
			List<String> fileList = dataTypeAndFilePathRelationMap.get(key);
			if (key.equals("user")) {
				for (String filePath : fileList) {
					List<String> lineList = FileOperatorUtil.readFileToList(
							filePath, "gbk");
					for (String line : lineList) {
						String id = RegexUtil.getRegexValue(line, "id=(\\d+),",
								1);
						String remark = RegexUtil.getRegexValue(line,
								"remark=(.*?),", 1);
						UserInfoPojo userPojo = new UserInfoPojo(id, remark);
						userPojoList.add(userPojo);
					}
				}
			} else {
				for (String filePath : fileList) {
					List<String> lineList = FileOperatorUtil.readFileToList(
							filePath, "gbk");
					// 获取该filePath的文件名称，用于作为该 文件对应的内容数据的id值
					String id = new File(filePath).getName().split("\\.")[0];
					for (String line : lineList) {
						try {
							line = removeSpecialSign(line);
							lineXmlCounter++;
							String content = Dom4JUtil.getElementTextByRoot(
									line, "content");
							String time = Dom4JUtil.getElementTextByRoot(line,
									"time");
							String repostsCount = Dom4JUtil
									.getElementTextByRoot(line, "repostsCount");
							String commentsCount = Dom4JUtil
									.getElementTextByRoot(line, "commentsCount");
							ContentInfoPojo contentPojo = new ContentInfoPojo(
									id, content, time, repostsCount,
									commentsCount);
							contentInfoPojoList.add(contentPojo);
							lineXmlCounterRight++;
						} catch (Exception e) {
							System.out.println(line);
							e.printStackTrace();
							lineXmlCounterError++;
						}
					}
				}
			}
		}
		System.out.println("lineXmlCounter=" + lineXmlCounter);
		System.out.println("lineXmlCounterRight=" + lineXmlCounterRight);
		System.out.println("lineXmlCounterError=" + lineXmlCounterError);

		UserAndContentJoinPojo joinPojo = new UserAndContentJoinPojo(
				userPojoList, contentInfoPojoList);
		return joinPojo;
	}

	public static String removeSpecialSign(String line) {
		String oldMatchContent = RegexUtil.getRegexValue(line, regex, 1);
		String newMatchContent = oldMatchContent.replaceAll("&", "&amp;")
				.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
				.replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
		
		line = line.replace(oldMatchContent, newMatchContent);
		return line;
	}

	public static Map<String, List<String>> getDataTypeAndFilePathRelationMap(
			String remarkPath) {
		List<String> dirList = FileOperatorUtil.getFirstLevelSubDir(remarkPath);
		for (String dir : dirList) {
			if (dataTypeAndFilePathRelationMap.containsKey(dir)) {
				String dataTypeRootDir = remarkPath + "/" + dir;
				List<String> subTextFileList = FileOperatorUtil.getAllSubFiles(
						dataTypeRootDir, true, null);
				dataTypeAndFilePathRelationMap.put(dir, subTextFileList);
			}
		}
		return dataTypeAndFilePathRelationMap;
	}

	public static void outputToFile(UserAndContentJoinPojo joinPojo,
			String destFilePath4User, String destFilePath4Content,
			String charset) throws UnsupportedEncodingException, IOException {
		// step1:先将user类对应的结构化集合输出到user.txt中,在此处的结构化文本输出时若有字段分隔行均用"\001"不可间空白字符
		List<UserInfoPojo> userPojoList = joinPojo.getUserPojoList();

		StringBuilder stringBuilder = new StringBuilder();
		int lineCount = 0;
		for (UserInfoPojo pojo : userPojoList) {
			String line = pojo.getId() + StaticValue.SEPARATOR_COLUMN_SIGN
					+ pojo.getRemark();
			if (lineCount > 0) {
				stringBuilder.append(StaticValue.SEPARATOR_LINE_SIGN);
			}
			stringBuilder.append(line);
			lineCount++;
		}
		FileOperatorUtil.writeStrToFile(destFilePath4User,
				stringBuilder.toString(), StaticValue.CHARSET_DEFAULT);

		// step2:先将content类对应的结构化集合输出到content.txt中,在此处的结构化文本输出时若有字段分隔行均用"\001"不可间空白字符
		List<ContentInfoPojo> contentPojoList = joinPojo.getContentPojoList();
		stringBuilder = new StringBuilder();
		lineCount = 0;
		for (ContentInfoPojo contentPojo : contentPojoList) {
			String line = contentPojo.getId()
					+ StaticValue.SEPARATOR_COLUMN_SIGN
					+ contentPojo.getContent()
					+ StaticValue.SEPARATOR_COLUMN_SIGN
					+ contentPojo.getRepostsCount()
					+ StaticValue.SEPARATOR_COLUMN_SIGN
					+ contentPojo.getCommentsCount()
					+ StaticValue.SEPARATOR_COLUMN_SIGN + contentPojo.getTime();
			if (lineCount > 0) {
				stringBuilder.append(StaticValue.SEPARATOR_LINE_SIGN);
			}
			stringBuilder.append(line);
			lineCount++;
		}
		FileOperatorUtil.writeStrToFile(destFilePath4Content,
				stringBuilder.toString(), StaticValue.CHARSET_DEFAULT);
	}

	public static void startProcess(String remarkRootDir,
			String destFilePath4User, String destFilePath4Content)
			throws IOException {
		// 得到数据类型与其对应的文件集合的对应关系
		Map<String, List<String>> dataTypeAndFilePathRelationMap = DataProcessManager
				.getDataTypeAndFilePathRelationMap(remarkRootDir);

		// 将type和filepath的对应关系，转化成类别与对象集合的关系，即结构化问题
		UserAndContentJoinPojo joinPojo = DataProcessManager
				.getConstructObjList(dataTypeAndFilePathRelationMap);

		DataProcessManager.outputToFile(joinPojo, destFilePath4User,
				destFilePath4Content, StaticValue.CHARSET_DEFAULT);
	}

	public static void main(String[] args) throws IOException,
			DocumentException {
		String rootFileDir = "weibodata";
		File file =new File(rootFileDir);
		
		List<String> resultFileList = new ArrayList<String>();
		if (file.isDirectory()) {
			for (File fileInner : file.listFiles()) {
				resultFileList.add(fileInner.toString());
			}
		}

		// 打印所有结果
		for (String rootDir : resultFileList) {
			System.out.println(rootDir);

			// 得到数据类型与其对应的文件集合的对应关系
			dataTypeAndFilePathRelationMap = getDataTypeAndFilePathRelationMap(rootDir);

			// 将type和filepath的对应关系，转化成类别与对象集合的关系，即结构化问题
			UserAndContentJoinPojo joinPojo = getConstructObjList(dataTypeAndFilePathRelationMap);

			// 将结构化的对象数据输出到指定的介质上(在此处是文本文件）
			String destFilePath4User = "user.txt";
			String destFilePath4Content = "content.txt";
			outputToFile(joinPojo, destFilePath4User, destFilePath4Content,
					StaticValue.CHARSET_DEFAULT);
			System.out.println("all done!");

		}

		// 定义数据文件夹的名称
//		String rootDir = "房地产";
//		// 得到数据类型与其对应的文件集合的对应关系
//		dataTypeAndFilePathRelationMap = getDataTypeAndFilePathRelationMap(rootDir);
//
//		// 将type和filepath的对应关系，转化成类别与对象集合的关系，即结构化问题
//		UserAndContentJoinPojo joinPojo = getConstructObjList(dataTypeAndFilePathRelationMap);
//
//		// 将结构化的对象数据输出到指定的介质上(在此处是文本文件）
//		String destFilePath4User = "user.txt";
//		String destFilePath4Content = "content.txt";
//		outputToFile(joinPojo, destFilePath4User, destFilePath4Content,
//				StaticValue.CHARSET_DEFAULT);
//		System.out.println("all done!");
	}
}
