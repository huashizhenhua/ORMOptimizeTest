package com.tencent.orm;

import java.util.ArrayList;

/**
 * @auther zhenhuachen
 * @time 2014-12-18 下午12:17:14
 */
public class OrmTable {

	public static class Properties {
		public int ordinal;
		public Class<?> type;
		public String name;
		public boolean primaryKey;
		public String columnName;
		public String defaultValue;
	}
	
	/**
	 * 表名
	 */
	public String tableName;
	
	/**
	 * 冲突策略
	 */
	public String conflictClause;
	
	/**
	 * 唯一键
	 */
	public ArrayList<String> pkColumnNames = new ArrayList<String>(); 

	public ArrayList<Properties> pkColumns = new ArrayList<Properties>();
	
	public ArrayList<Properties> columns = new ArrayList<Properties>();
	
	public ArrayList<Properties> notColumns = new ArrayList<Properties>();
	

}
