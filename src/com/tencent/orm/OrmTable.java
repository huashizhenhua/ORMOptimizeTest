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
	}
	
	
	public String tableName;
	
	public ArrayList<Properties> properties;
	
	public ArrayList<Properties> pkColumns;

}
