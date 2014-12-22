package com.tencent.orm;

import java.io.File;

/**
 * @auther zhenhuachen
 * @time 2014-12-18 上午11:16:32
 */
public class FileUtils {

	public static File[] list(String pathname) {
		File parentFile = new File(pathname);
		if (parentFile.exists() && parentFile.isDirectory()) {
			return parentFile.listFiles();
		} else {
			return null;
		}
	}

}
