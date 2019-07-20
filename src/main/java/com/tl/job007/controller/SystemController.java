package com.tl.job007.controller;

import java.io.IOException;

import com.tl.job007.manager.DataProcessManager;

public class SystemController {
	public static void main(String[] args) throws IOException {
		if (args == null || args.length != 3) {
			System.out.println("参数错误，请检查输入参数的正确性!");
			System.exit(-1);
		}
		
		String remarkRootDir = args[0];
		String destFilePath4User = args[1];
		String destFilePath4Content = args[2];
		
		DataProcessManager.startProcess(remarkRootDir, destFilePath4User,
				destFilePath4Content);
		
		System.out.println("all done!!!");

	}
}
