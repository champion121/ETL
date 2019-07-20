package com.tl.job007.manager;

import java.io.IOException;

import org.dom4j.DocumentException;

public class DataProcessManagerTest {
	
	public static void main(String[] args) throws IOException,
			DocumentException {
		// 定义数据文件夹的名称
//		String remarkRootDir = "房地产";
	
		String remarkRootDir = "风水";
		// 将结构化的对象数据输出到指定的介质上(在此处是文本文件）
		String destFilePath4User = "user.txt";
		String destFilePath4Content = "content.txt";
		
		DataProcessManager.startProcess(remarkRootDir, destFilePath4User, destFilePath4Content);
		
		System.out.println("all done!");
	}
}
