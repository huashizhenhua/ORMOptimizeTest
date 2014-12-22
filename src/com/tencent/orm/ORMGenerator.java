package com.tencent.orm;

import java.io.File;

import orm.reflect.Log;

/**
 * @auther zhenhuachen
 * @time 2014-12-17 下午6:39:30
 */

class EntityReader {
	
	public String mPath;
	
	public EntityReader(String path) {
		
		this.mPath = path;
		File entityDir = new File(path);
		if (entityDir.exists() && entityDir.isDirectory()) {
			File[] files = entityDir.listFiles();
			for(File f : files) {
				parse(f);
			}
		} else {
			ORMGenerator.debug("entity folder invalid");
		}
	}
	
	public void parse(File f) {
	}
}

public class ORMGenerator {
	
	public static final String TAG = "ORMGenerator";
	
	public static void debug(String tag, String msg) {
		System.out.println(String.format("[%s] %s", tag , msg));
	}
	
	public static void debug(String msg) {
		debug("ORMGenerator", msg);
	}
	
	public static void main(String[] args) {
	}
	
}
